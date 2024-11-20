package com.kjm.Weather_wear.controller;


import com.kjm.Weather_wear.dto.WeatherResponseDTO;
import com.kjm.Weather_wear.entity.Region;
import com.kjm.Weather_wear.entity.Weather;
import com.kjm.Weather_wear.repository.RegionRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@RestController
@RequestMapping("/api/weather")
@RequiredArgsConstructor
public class WeatherApiController {

    private final EntityManager em;
    private final RegionRepository regionRepository;

    @Value("${weatherApi.serviceKey}")
    private String serviceKey;

    @GetMapping
    @Transactional
    public ResponseEntity<WeatherResponseDTO> getRegionWeather(@RequestParam int nx, @RequestParam int ny) {

        // 1. nx, ny를 기준으로 지역 조회
        Region region = regionRepository.findAll()
                .stream()
                .filter(r -> r.getNx() == nx && r.getNy() == ny)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당 좌표에 해당하는 지역이 없습니다."));

        // 지역명을 가져오기 위해 toString() 활용
        String regionName = region.toString();

        log.info("Region found: {}", region);

        // 2. 요청 시각 조회
        LocalDateTime now = LocalDateTime.now();
        String yyyyMMdd = now.format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        // 1. 유효한 base_time 배열
        int[] validBaseTimes = {2, 5, 8, 11, 14, 17, 20, 23}; // 제공되는 base_time (시간)

        int hour = now.getHour();
        int min = now.getMinute();
        int baseTime = 0;


        for (int i = 0; i < validBaseTimes.length; i++) {
            int validHour = validBaseTimes[i];
            // 현재 시간이 base_time의 제공 시간 이후라면 선택
            if (hour > validHour || (hour == validHour && min >= 10)) {
                baseTime = validHour;
            } else {
                break; // 현재 시간이 제공 기준 시간보다 작다면 이전 base_time을 유지
            }
        }

        // 자정 이전(첫 번째 제공 시간 이전) 처리
        if (hour < validBaseTimes[0] || (hour == validBaseTimes[0] && min < 10)) {
            baseTime = validBaseTimes[validBaseTimes.length - 1]; // 전날 마지막 base_time
            yyyyMMdd = now.minusDays(1).format(DateTimeFormatter.ofPattern("yyyyMMdd")); // 날짜를 전날로 변경
        }

        String hourStr = String.format("%02d00", baseTime); // base_time을 "0200" 형식으로 변환
        // 3. currentChangeTime 정의
        String currentChangeTime = yyyyMMdd + " " + String.format("%02d", baseTime);


        log.info("Calculated base_date: {}, base_time: {}", yyyyMMdd, hourStr);

        // 3. 기준 시각 조회 자료가 이미 존재하고 있다면 API 요청 없이 기존 자료 그대로 넘김
        Weather prevWeather = region.getWeather();
        if (prevWeather != null && prevWeather.getLastUpdateTime() != null) {
            if (prevWeather.getLastUpdateTime().equals(currentChangeTime)) {
                log.info("기존 자료를 재사용합니다.");
                WeatherResponseDTO dto = WeatherResponseDTO.builder()
                        .weather(prevWeather)
                        .message("OK").build();
                return ResponseEntity.ok(dto);
            }
        }

        log.info("API 요청 발송 >>> 지역: {}, 연월일: {}, 시각: {}", region, yyyyMMdd, hourStr);

        try {
            // UriComponentsBuilder 사용
            String urlString = UriComponentsBuilder.fromHttpUrl("http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getVilageFcst")
                    .queryParam("serviceKey", serviceKey)
                    .queryParam("pageNo", "1")
                    .queryParam("numOfRows", "1000")
                    .queryParam("dataType", "JSON")
                    .queryParam("base_date", yyyyMMdd)
                    .queryParam("base_time", hourStr)
                    .queryParam("nx", nx)
                    .queryParam("ny", ny)
                    .build()
                    .toUriString();

            URL url = new URL(urlString);
            log.info("request url: {}", url);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-type", "application/json");

            BufferedReader rd;
            if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
                rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            } else {
                rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            }
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = rd.readLine()) != null) {
                sb.append(line);
            }
            rd.close();
            conn.disconnect();
            String data = sb.toString();

            log.info("API Response Data: {}", data);

            //// 응답 수신 완료 ////
            //// 응답 결과를 JSON 파싱 ////

            Double temp = null;
            Double minTemp = null;
            Double maxTemp = null;
            Double rainAmount = null;
            Double humid = null;
            Double windSpeed = null;
            Double rainProbability = null; // 선언 및 초기화
            String rainType = null;
            String skyCondition = null;


            JSONObject jObject = new JSONObject(data);
            JSONObject response = jObject.getJSONObject("response");
            JSONObject body = response.getJSONObject("body");
            JSONObject items = body.getJSONObject("items");
            JSONArray jArray = items.getJSONArray("item");

            for(int i = 0; i < jArray.length(); i++) {
                JSONObject obj = jArray.getJSONObject(i);
                String category = obj.getString("category");
                double fcstValue = obj.optDouble("fcstValue", -1);

                switch (category) {
                    case "TMP":
                        temp = fcstValue;
                        break;
                    case "TMN":
                        minTemp = fcstValue;
                        break;
                    case "TMX":
                        maxTemp = fcstValue;
                        break;
                    case "PCP":
                        rainAmount = fcstValue == -1 ? 0.0 : fcstValue;
                        break;
                    case "REH":
                        humid = fcstValue;
                        break;
                    case "WSD":
                        windSpeed = fcstValue;
                        break;
                    case "POP":
                        rainProbability = fcstValue;
                        break;
                    case "PTY":
                        rainType = String.valueOf((int) fcstValue); // 코드값 그대로 저장
                        break;
                    case "SKY":
                        skyCondition = String.valueOf((int) fcstValue); // 코드값 그대로 저장
                        break;
                }
            }

            Weather weather = new Weather(temp, minTemp, maxTemp, rainAmount, humid, windSpeed,
                    rainProbability, rainType, skyCondition, currentChangeTime);
            region.updateRegionWeather(weather); // DB 업데이트
            regionRepository.save(region);

            WeatherResponseDTO dto = WeatherResponseDTO.builder()
                    .weather(weather)
                    .regionName(regionName)
                    .message("OK")
                    .build();
            return ResponseEntity.ok(dto);
        } catch (IOException e) {
            WeatherResponseDTO dto = WeatherResponseDTO.builder()
                    .weather(null)
                    .regionName(regionName)
                    .message("날씨 정보를 불러오는 중 오류가 발생했습니다")
                    .build();
            return ResponseEntity.ok(dto);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
}
