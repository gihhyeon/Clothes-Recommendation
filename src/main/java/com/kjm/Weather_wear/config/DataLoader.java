package com.kjm.Weather_wear.config;

import com.kjm.Weather_wear.entity.Clothing;
import com.kjm.Weather_wear.repository.ClothingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataLoader {

    private final ClothingRepository clothingRepository;

    public DataLoader(ClothingRepository clothingRepository) {
        this.clothingRepository = clothingRepository;
    }

    @Autowired
    public void run(String... args) throws Exception {
        // 상의
        addClothingIfNotExists("상의", "반팔", 27, 50);
        addClothingIfNotExists("상의", "나시", 27, 50);
        addClothingIfNotExists("상의", "얇은 셔츠", 23, 26);
        addClothingIfNotExists("상의", "롱슬리브", 20, 22);
        addClothingIfNotExists("상의", "니트", 17, 19);
        addClothingIfNotExists("상의", "맨투맨", 17, 19);
        addClothingIfNotExists("상의", "후드티", 17, 19);

        // 하의
        addClothingIfNotExists("하의", "반바지", 27, 50);
        addClothingIfNotExists("하의", "청바지", 17, 26);
        addClothingIfNotExists("하의", "슬랙스", 20, 26);

        // 아우터
        addClothingIfNotExists("아우터", "바람막이", 23, 26);
        addClothingIfNotExists("아우터", "얇은 자켓", 20, 22);
        addClothingIfNotExists("아우터", "블루종", 12, 16);
        addClothingIfNotExists("아우터", "트렌치코트", 9, 16);
        addClothingIfNotExists("아우터", "경량 패딩", 9, 16);
        addClothingIfNotExists("아우터", "코트", 5, 8);
        addClothingIfNotExists("아우터", "패딩", -10, 4);
    }

    // 중복 확인 후 데이터 삽입
    private void addClothingIfNotExists(String category, String itemName, int minTemp, int maxTemp) {
        if (!clothingRepository.existsByCategoryAndItemName(category, itemName)) {
            clothingRepository.save(new Clothing(category, itemName, minTemp, maxTemp));
        }
    }
}