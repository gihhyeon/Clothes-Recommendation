package com.kjm.Weather_wear.config;

import com.kjm.Weather_wear.entity.Clothing;
import com.kjm.Weather_wear.repository.ClothingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final ClothingRepository clothingRepository;

    @Override
    public void run(String... args) throws Exception {
        // 28도 이상
        addClothingIfNotExists("상의", "민소매", 28, 50);
        addClothingIfNotExists("상의", "반팔 티셔츠", 28, 50);
        addClothingIfNotExists("하의", "반바지", 28, 50);
        addClothingIfNotExists("하의", "짧은 치마", 28, 50);
        addClothingIfNotExists("복합", "민소매 원피스", 28, 50);
        addClothingIfNotExists("복합", "린넨 재질 옷", 28, 50);

        // 23~27도
        addClothingIfNotExists("상의", "반팔 티셔츠", 23, 27);
        addClothingIfNotExists("상의", "얇은 셔츠", 23, 27);
        addClothingIfNotExists("상의", "얇은 긴팔 티셔츠", 23, 27);
        addClothingIfNotExists("하의", "반바지", 23, 27);
        addClothingIfNotExists("하의", "면바지", 23, 27);

        // 20~22도
        addClothingIfNotExists("아우터", "얇은 가디건", 20, 22);
        addClothingIfNotExists("상의", "긴팔 티셔츠", 20, 22);
        addClothingIfNotExists("상의", "셔츠", 20, 22);
        addClothingIfNotExists("상의", "블라우스", 20, 22);
        addClothingIfNotExists("상의", "후드티", 20, 22);
        addClothingIfNotExists("하의", "면바지", 20, 22);
        addClothingIfNotExists("하의", "슬랙스", 20, 22);
        addClothingIfNotExists("하의", "7부 바지", 20, 22);
        addClothingIfNotExists("하의", "청바지", 20, 22);

        // 17~19도
        addClothingIfNotExists("아우터", "얇은 니트", 17, 19);
        addClothingIfNotExists("아우터", "얇은 가디건", 17, 19);
        addClothingIfNotExists("아우터", "얇은 재킷", 17, 19);
        addClothingIfNotExists("아우터", "바람막이", 17, 19);
        addClothingIfNotExists("상의", "후드티", 17, 19);
        addClothingIfNotExists("상의", "맨투맨", 17, 19);
        addClothingIfNotExists("하의", "긴바지", 17, 19);
        addClothingIfNotExists("하의", "청바지", 17, 19);
        addClothingIfNotExists("하의", "슬랙스", 17, 19);
        addClothingIfNotExists("하의", "스키니진", 17, 19);

        // 12~16도
        addClothingIfNotExists("아우터", "재킷", 12, 16);
        addClothingIfNotExists("아우터", "가디건", 12, 16);
        addClothingIfNotExists("아우터", "야상", 12, 16);
        addClothingIfNotExists("상의", "스웨트 셔츠", 12, 16);
        addClothingIfNotExists("상의", "맨투맨", 12, 16);
        addClothingIfNotExists("상의", "셔츠", 12, 16);
        addClothingIfNotExists("상의", "기모 후드티", 12, 16);
        addClothingIfNotExists("하의", "청바지", 12, 16);
        addClothingIfNotExists("하의", "면바지", 12, 16);
        addClothingIfNotExists("복합", "스타킹", 12, 16);
        addClothingIfNotExists("복합", "니트", 12, 16);

        // 9~11도
        addClothingIfNotExists("아우터", "재킷", 9, 11);
        addClothingIfNotExists("아우터", "야상", 9, 11);
        addClothingIfNotExists("아우터", "점퍼", 9, 11);
        addClothingIfNotExists("아우터", "트렌치 코트", 9, 11);
        addClothingIfNotExists("하의", "청바지", 9, 11);
        addClothingIfNotExists("하의", "면바지", 9, 11);
        addClothingIfNotExists("복합", "검은색 스타킹", 9, 11);
        addClothingIfNotExists("복합", "기모 바지", 9, 11);
        addClothingIfNotExists("복합", "레이어드 니트", 9, 11);

        // 5~8도
        addClothingIfNotExists("아우터", "코트", 5, 8);
        addClothingIfNotExists("아우터", "가죽 재킷", 5, 8);
        addClothingIfNotExists("하의", "레깅스", 5, 8);
        addClothingIfNotExists("하의", "청바지", 5, 8);
        addClothingIfNotExists("하의", "두꺼운 바지", 5, 8);
        addClothingIfNotExists("복합", "기모 스타킹", 5, 8);
        addClothingIfNotExists("복합", "스카프", 5, 8);
        addClothingIfNotExists("복합", "플리스", 5, 8);
        addClothingIfNotExists("복합", "내복", 5, 8);

        // 4도 이하
        addClothingIfNotExists("아우터", "패딩", -10, 4);
        addClothingIfNotExists("아우터", "두꺼운 코트", -10, 4);
        addClothingIfNotExists("복합", "누빔", -10, 4);
        addClothingIfNotExists("복합", "내복", -10, 4);
        addClothingIfNotExists("복합", "목도리", -10, 4);
        addClothingIfNotExists("복합", "장갑", -10, 4);
        addClothingIfNotExists("복합", "기모 스타킹", -10, 4);
        addClothingIfNotExists("복합", "방한용품", -10, 4);
    }

    private void addClothingIfNotExists(String category, String itemName, int minTemp, int maxTemp) {
        if (!clothingRepository.existsByCategoryAndItemName(category, itemName)) {
            clothingRepository.save(new Clothing(category, itemName, minTemp, maxTemp));
        } else {
            // 이미 존재하는 경우, 업데이트 로직 추가
            Clothing existingClothing = clothingRepository.findByCategoryAndItemName(category, itemName);
            if (existingClothing != null) {
                existingClothing.setMinTemp(minTemp);
                existingClothing.setMaxTemp(maxTemp);
                clothingRepository.save(existingClothing);
            }
        }
    }
}