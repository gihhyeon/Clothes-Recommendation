package com.kjm.Weather_wear.controller;

import com.kjm.Weather_wear.dto.WeatherResponseDTO;
import com.kjm.Weather_wear.entity.Region;
import com.kjm.Weather_wear.entity.Weather;
import com.kjm.Weather_wear.repository.RegionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Slf4j
@RestController
@RequestMapping("/api/weather")
@RequiredArgsConstructor
public class WeatherApiController {

    private final RegionRepository regionRepository;

    @Value("${weatherApi.serviceKey}")
    private String serviceKey;

    @GetMapping
    @Transactional
    public ResponseEntity<List<WeatherResponseDTO>> getRegionWeather(@RequestParam int nx, @RequestParam int ny) {

        // 1. nx, ny를 기준으로 지역 조회
        Region region = regionRepository.findAll()
                .stream()
                .filter(r -> r.getNx() == nx && r.getNy() == ny)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당 좌표에 해당하는 지역이 없습니다."));

        String regionName = region.toString();
        log.info("Region found: {}", region);

        // 2. 요청 시각 계산
        LocalDateTime now = LocalDateTime.now();
        String baseDate = calculateBaseDate(now);
        String baseTime = calculateBaseTime(now);

        // 3. 기존 데이터 확인
        List<Weather> weatherList = region.getWeatherList();
        Weather prevWeather = weatherList.isEmpty() ? null : weatherList.get(weatherList.size() - 1);

        if (prevWeather != null && prevWeather.getLastUpdateTime() != null) {
            if (prevWeather.getLastUpdateTime().equals(baseDate + " " + baseTime)) {
                log.info("기존 데이터를 반환합니다.");
                return ResponseEntity.ok(convertToWeatherResponseDTOs(weatherList, regionName));
            }
        }

        // 4. API 요청
        log.info("API 요청 발송 >>> 지역: {}, 연월일: {}, 시각: {}", region, baseDate, baseTime);

        try {
            List<Weather> newWeatherList = fetch72HourWeatherFromApi(region, baseDate, baseTime);

            // 기존 데이터 삭제 후 새 데이터 추가
            region.getWeatherList().clear();
            newWeatherList.forEach(region::updateRegionWeather);
            regionRepository.save(region);

            // 응답 생성
            return ResponseEntity.ok(convertToWeatherResponseDTOs(newWeatherList, regionName));

        } catch (Exception e) {
            log.error("날씨 정보를 불러오는 중 오류 발생: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    private List<Weather> fetch72HourWeatherFromApi(Region region, String baseDate, String baseTime) throws IOException {
        String urlString = UriComponentsBuilder.fromHttpUrl("http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getVilageFcst")
                .queryParam("serviceKey", serviceKey)
                .queryParam("pageNo", "1")
                .queryParam("numOfRows", "1000")
                .queryParam("dataType", "JSON")
                .queryParam("base_date", baseDate)
                .queryParam("base_time", baseTime)
                .queryParam("nx", region.getNx())
                .queryParam("ny", region.getNy())
                .build()
                .toUriString();

        log.info("Request URL: {}", urlString);

        HttpURLConnection conn = (HttpURLConnection) new URL(urlString).openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");

        BufferedReader rd = new BufferedReader(new InputStreamReader(
                conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300
                        ? conn.getInputStream()
                        : conn.getErrorStream()));

        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();

        return parse72HourWeatherData(sb.toString(), region);
    }

    private List<Weather> parse72HourWeatherData(String jsonResponse, Region region) {
        JSONObject response = new JSONObject(jsonResponse).optJSONObject("response");
        if (response == null || !response.has("body")) {
            log.error("API 응답에 body가 없습니다.");
            return Collections.emptyList();
        }

        JSONObject body = response.getJSONObject("body");
        JSONArray items = body.optJSONObject("items").optJSONArray("item");
        if (items == null) {
            log.error("API 응답에 item 데이터가 없습니다.");
            return Collections.emptyList();
        }

        String lastUpdateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd HHmm"));

        Map<String, Weather> hourlyData = new LinkedHashMap<>();
        for (int i = 0; i < items.length(); i++) {
            JSONObject item = items.getJSONObject(i);
            String fcstDate = item.getString("fcstDate"); // 예보일자
            String fcstTime = item.getString("fcstTime"); // 예보시간
            String category = item.getString("category"); // 데이터 카테고리
            double fcstValue = item.optDouble("fcstValue", -1); // 값

            String dateTimeKey = fcstDate + fcstTime; // 고유 키

            // 기존 또는 새로운 Weather 객체 가져오기
            Weather weather = hourlyData.getOrDefault(dateTimeKey, new Weather());
            weather.setRegion(region);
            weather.setForecastDate(fcstDate); // 예보일자 설정
            weather.setForecastTime(fcstTime); // 예보시간 설정
            weather.setLastUpdateTime(lastUpdateTime); // 마지막 업데이트 시간 설정

            // 데이터 카테고리에 따라 Weather 객체 업데이트
            switch (category) {
                case "TMP":
                    weather.setTemp(fcstValue);
                    break;
                case "TMN":
                    log.info("TMN (최저기온): {}", fcstValue);
                    weather.setMinTemp(fcstValue);
                    break;
                case "TMX":
                    log.info("TMN (최고기온): {}", fcstValue);
                    weather.setMaxTemp(fcstValue);
                    break;
                case "PCP":
                    weather.setRainAmount(fcstValue == -1 ? 0.0 : fcstValue);
                    break;
                case "REH":
                    weather.setHumid(fcstValue);
                    break;
                case "WSD":
                    weather.setWindSpeed(fcstValue);
                    break;
                case "POP":
                    weather.setRainProbability(fcstValue);
                    break;
                case "PTY":
                    weather.setRainType(String.valueOf((int) fcstValue));
                    break;
                case "SKY":
                    weather.setSkyCondition(String.valueOf((int) fcstValue));
                    break;
            }

            hourlyData.put(dateTimeKey, weather);
        }

        return new ArrayList<>(hourlyData.values()).stream()
                .filter(weather -> weather.getForecastDate() != null && weather.getForecastTime() != null) // 필터링
                .sorted(Comparator.comparing(Weather::getForecastDate).thenComparing(Weather::getForecastTime)) // 정렬
                .limit(72) // 72시간 데이터 제한
                .toList();
    }

    private List<WeatherResponseDTO> convertToWeatherResponseDTOs(List<Weather> weatherList, String regionName) {
        return weatherList.stream()
                .map(weather -> WeatherResponseDTO.builder()
                        .regionName(regionName)
                        .weather(weather)
                        .build())
                .toList();
    }

    private String calculateBaseDate(LocalDateTime now) {
        return now.minusHours(1).format(DateTimeFormatter.ofPattern("yyyyMMdd"));
    }

    private String calculateBaseTime(LocalDateTime now) {
        int[] validBaseTimes = {2, 5, 8, 11, 14, 17, 20, 23};
        int hour = now.getHour();
        int min = now.getMinute();
        int baseTime = 0;

        for (int validBaseTime : validBaseTimes) {
            if (hour > validBaseTime || (hour == validBaseTime && min >= 10)) {
                baseTime = validBaseTime;
            }
        }
        return String.format("%02d00", baseTime);
    }
}