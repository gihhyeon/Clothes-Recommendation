package com.kjm.Weather_wear.controller;


import com.kjm.Weather_wear.dto.WeatherResponseDTO;
import com.kjm.Weather_wear.entity.Region;
import com.kjm.Weather_wear.entity.Weather;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
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

@Slf4j
@RestController
@RequestMapping("/api/weather")
@RequiredArgsConstructor
public class WeatherApiController {

    private final EntityManager em;

    @Value("${weatherApi.serviceKey}")
    private String serviceKey;

    @GetMapping
    @Transactional
    public ResponseEntity<WeatherResponseDTO> getRegionWeather(@RequestParam Long regionId) {

        // 1. 날씨 정보를 요청한 지역 조회
        Region region = em.find(Region.class, regionId);

        // 2. 요청 시각 조회
        LocalDateTime now = LocalDateTime.now();
        String yyyyMMdd = now.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        int hour = now.getHour();
        int min = now.getMinute();
        if (min <= 30) {
            hour -= 1;
        }
        String hourStr = hour + "00";
        String nx = Integer.toString(region.getNx());
        String ny = Integer.toString(region.getNy());
        String currentChangeTime = now.format(DateTimeFormatter.ofPattern("yy.MM.dd HH"));

        // 기준 시각 조회 자료가 이미 존재하고 있다면 API 요청 없이 기존 자료 그대로 넘김
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
            String urlString = UriComponentsBuilder.fromHttpUrl("http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getUltraSrtNcst")
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

            //// 응답 수신 완료 ////
            //// 응답 결과를 JSON 파싱 ////

            Double temp = null;
            Double humid = null;
            Double rainAmount = null;

            JSONObject jObject = new JSONObject(data);
            JSONObject response = jObject.getJSONObject("response");
            JSONObject body = response.getJSONObject("body");
            JSONObject items = body.getJSONObject("items");
            JSONArray jArray = items.getJSONArray("item");

            for(int i = 0; i < jArray.length(); i++) {
                JSONObject obj = jArray.getJSONObject(i);
                String category = obj.getString("category");
                double obsrValue = obj.getDouble("obsrValue");

                switch (category) {
                    case "T1H":
                        temp = obsrValue;
                        break;
                    case "RN1":
                        rainAmount = obsrValue;
                        break;
                    case "REH":
                        humid = obsrValue;
                        break;
                }
            }

            Weather weather = new Weather(temp, rainAmount, humid, currentChangeTime);
            region.updateRegionWeather(weather); // DB 업데이트
            WeatherResponseDTO dto = WeatherResponseDTO.builder()
                    .weather(weather)
                    .message("OK").build();
            return ResponseEntity.ok(dto);
        } catch (IOException e) {
            WeatherResponseDTO dto = WeatherResponseDTO.builder()
                    .weather(null)
                    .message("날씨 정보를 불러오는 중 오류가 발생했습니다").build();
            return ResponseEntity.ok(dto);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
}
