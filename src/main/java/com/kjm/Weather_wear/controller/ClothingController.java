package com.kjm.Weather_wear.controller;

import com.kjm.Weather_wear.entity.Weather;
import com.kjm.Weather_wear.service.ClothingRecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clothing")
public class ClothingController {

    private final ClothingRecommendationService clothingRecommendationService;

    @Autowired
    public ClothingController(ClothingRecommendationService clothingRecommendationService) {
        this.clothingRecommendationService = clothingRecommendationService;
    }

    // 의류 추천 (Weather와 사용자 타입에 따른 의류 목록 제공)
    //recommendClothing(x, y, loginvo)
    @PostMapping("/recommend")
    public ResponseEntity<List<String>> recommendClothing(@RequestBody Weather weather, @RequestParam String userType) {
        try {
            //지역 및 기온정보 받아오기 weatherInfoService(x, y)  return 값은 지역명, 기온
            //List<String > recommendations = clothingRecommendationService.weatherInfoService(weather, userType);

            // 추천 옷 받아오기   recommendClothing(기온, 로그인정보 유저타입)  retrun 값은 추천옷
            List<String > recommendations = clothingRecommendationService.recommendClothing(weather, userType);


            //객체(entitiy) 하나 만들어서 정보 담아서 프론트에 쏴주기

            return new ResponseEntity<>(recommendations, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}

/**
 * 1. 파라미터로 사용자로부터 로그인 정보, x, y 좌표 받아오기
 * 2. x, y 좌표로 db에 적재되어 있는 지역 정보 및 기온 받아오기
 * 3. recommendClothing 메소드 활용해서 기온 정보랑 로그인 정보(유저 타입) 받아오기
 * 4. 프론트에 뿌리기
 *
 * 프론트에서 받아오는 정보를 담을 엔티티 만들기
 */