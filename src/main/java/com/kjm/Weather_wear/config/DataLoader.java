package com.kjm.Weather_wear.config;

import com.kjm.Weather_wear.entity.Clothing;
import com.kjm.Weather_wear.repository.ClothingRepository;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final ClothingRepository clothingRepository;

    @Override
    public void run(String... args) throws Exception {
        // 28도 이상
        addClothingIfNotExists("상의", "민소매", 27.0, 50.0);
        addClothingIfNotExists("하의", "짧은 치마", 27.0, 50.0);
        addClothingIfNotExists("복합", "민소매 원피스", 27.0, 50.0);
        addClothingIfNotExists("복합", "린넨 재질 옷", 27.0, 50.0);

        // 23~27도
        addClothingIfNotExists("상의", "얇은 셔츠", 23.0, 26.0);
        addClothingIfNotExists("상의", "얇은 긴팔 티셔츠", 23.0, 26.0);

        // 20~22도
        addClothingIfNotExists("아우터", "얇은 가디건", 20.0, 22.0);
        addClothingIfNotExists("상의", "긴팔 티셔츠", 20.0, 22.0);
        addClothingIfNotExists("상의", "셔츠", 20.0, 22.0);
        addClothingIfNotExists("상의", "블라우스", 20.0, 22.0);

        // 17~19도
        addClothingIfNotExists("아우터", "얇은 니트", 17.0, 19.0);
        addClothingIfNotExists("아우터", "얇은 가디건", 17.0, 19.0);
        addClothingIfNotExists("아우터", "얇은 재킷", 17.0, 19.0);
        addClothingIfNotExists("아우터", "바람막이", 17.0, 19.0);

        // 12~16도
        addClothingIfNotExists("아우터", "가디건", 12.0, 16.0);
        addClothingIfNotExists("상의", "셔츠", 12.0, 16.0);
        addClothingIfNotExists("복합", "스타킹", 12.0, 16.0);
        addClothingIfNotExists("복합", "니트", 12.0, 16.0);

        // 9~11도
        addClothingIfNotExists("아우터", "점퍼", 9.0, 11.0);
        addClothingIfNotExists("아우터", "트렌치 코트", 9.0, 11.0);

        //큰 범위
        addClothingIfNotExists("하의", "반바지", 25.0, 50.0);
        addClothingIfNotExists("상의", "반팔 티셔츠", 25.0, 50.0);
        addClothingIfNotExists("아우터", "야상", 9.0, 16.0);
        addClothingIfNotExists("아우터", "재킷", 9.0, 16.0);
        addClothingIfNotExists("복합", "플리스", 5.0, 16.0);
        addClothingIfNotExists("하의", "면바지", 5.0, 26.0);
        addClothingIfNotExists("상의", "맨투맨", 5.0, 22.0);
        addClothingIfNotExists("하의", "청바지", 5.0, 22.0);
        addClothingIfNotExists("하의", "슬랙스", 5.0, 22.0);
        addClothingIfNotExists("상의", "후드티", 5.0, 19.0);

        // 5~8도
        addClothingIfNotExists("아우터", "코트", 5.0, 8.0);
        addClothingIfNotExists("아우터", "가죽 재킷", 5.0, 8.0);
        addClothingIfNotExists("하의", "레깅스", 5.0, 8.0);
        addClothingIfNotExists("하의", "두꺼운 바지", 5.0, 8.0);

        // 4도 이하
        addClothingIfNotExists("아우터", "패딩", -20.0, 4.0);
        addClothingIfNotExists("아우터", "두꺼운 코트", -20.0, 4.0);
        addClothingIfNotExists("복합", "기모 스타킹", -20.0, 4.0);
        addClothingIfNotExists("상의", "기모 후드티", -20.0, 4.0);
        addClothingIfNotExists("복합", "기모 바지", -20.0, 4.0);
        addClothingIfNotExists("복합", "누빔", -20.0, 4.0);
        addClothingIfNotExists("복합", "내복", -20.0, 4.0);
        addClothingIfNotExists("복합", "목도리", -20.0, 4.0);
        addClothingIfNotExists("복합", "장갑", -20.0, 4.0);
        addClothingIfNotExists("복합", "기모 스타킹", -20.0, 4.0);
        addClothingIfNotExists("복합", "방한용품", -20.0, 4.0);

    }


    private void addClothingIfNotExists(String category, String itemName, Double minTemp, Double maxTemp) {
        if (!clothingRepository.existsByCategoryAndItemNameAndMinTempAndMaxTemp(category, itemName, minTemp, maxTemp)) {
            Clothing clothing = new Clothing();
            clothing.setCategory(category);
            clothing.setItemName(itemName);
            clothing.setMinTemp(minTemp);
            clothing.setMaxTemp(maxTemp);

            clothingRepository.save(clothing);
        } else {
            log.info("Clothing already exists: {} - {}", category, itemName);
        }
    }
}