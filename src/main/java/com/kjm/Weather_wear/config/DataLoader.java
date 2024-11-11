package com.kjm.Weather_wear.config;

import com.kjm.Weather_wear.entity.Clothing;
import com.kjm.Weather_wear.repository.ClothingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataLoader {

    @Autowired
    private ClothingRepository clothingRepository;

    @Bean
    public CommandLineRunner initDatabase() {
        return args -> {
            // 의류 리스트 생성
            addClothingIfNotExists("상의", "반팔");
            addClothingIfNotExists("상의", "나시");
            addClothingIfNotExists("상의", "얇은 셔츠");
            addClothingIfNotExists("상의", "셔츠");
            addClothingIfNotExists("상의", "롱슬리브");
            addClothingIfNotExists("상의", "맨투맨");
            addClothingIfNotExists("상의", "후드티");
            addClothingIfNotExists("상의", "얇은 니트");
            addClothingIfNotExists("상의", "니트");

            addClothingIfNotExists("하의", "반바지");
            addClothingIfNotExists("하의", "청바지");
            addClothingIfNotExists("하의", "치노 팬츠");
            addClothingIfNotExists("하의", "슬랙스");
            addClothingIfNotExists("하의", "조거 팬츠");

            addClothingIfNotExists("아우터", "바람막이");
            addClothingIfNotExists("아우터", "후드집업");
            addClothingIfNotExists("아우터", "블루종");
            addClothingIfNotExists("아우터", "데님 자켓");
            addClothingIfNotExists("아우터", "가죽 자켓");
            addClothingIfNotExists("아우터", "얇은 가디건");
            addClothingIfNotExists("아우터", "가디건");
            addClothingIfNotExists("아우터", "트렌치 코트");
            addClothingIfNotExists("아우터", "코트");
            addClothingIfNotExists("아우터", "무스탕");
            addClothingIfNotExists("아우터", "경량 패딩");
            addClothingIfNotExists("아우터", "패딩");
        };
    }

    // 중복 확인 후 의류 추가
    private void addClothingIfNotExists(String category, String itemName) {
        if (!clothingRepository.existsByCategoryAndItemName(category, itemName)) {
            clothingRepository.save(new Clothing(category, itemName));
        }
    }
}