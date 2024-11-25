package com.kjm.Weather_wear.config;

import com.kjm.Weather_wear.entity.Clothing;
import com.kjm.Weather_wear.repository.ClothingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final ClothingRepository clothingRepository;

    @Override
    public void run(String... args) throws Exception {
        // 28도 이상
        addClothingIfNotExists("상의", "민소매", 28.0, 50.0);
        addClothingIfNotExists("상의", "반팔 티셔츠", 28.0, 50.0);
        addClothingIfNotExists("하의", "반바지", 28.0, 50.0);
        addClothingIfNotExists("하의", "짧은 치마", 28.0, 50.0);
        addClothingIfNotExists("복합", "민소매 원피스", 28.0, 50.0);
        addClothingIfNotExists("복합", "린넨 재질 옷", 28.0, 50.0);

        // 23~27도
        addClothingIfNotExists("상의", "반팔 티셔츠", 23.0, 27.0);
        addClothingIfNotExists("상의", "얇은 셔츠", 23.0, 27.0);
        addClothingIfNotExists("상의", "얇은 긴팔 티셔츠", 23.0, 27.0);
        addClothingIfNotExists("하의", "반바지", 23.0, 27.0);
        addClothingIfNotExists("하의", "면바지", 23.0, 27.0);

        // 20~22도
        addClothingIfNotExists("아우터", "얇은 가디건", 20.0, 22.0);
        addClothingIfNotExists("상의", "긴팔 티셔츠", 20.0, 22.0);
        addClothingIfNotExists("상의", "셔츠", 20.0, 22.0);
        addClothingIfNotExists("상의", "블라우스", 20.0, 22.0);
        addClothingIfNotExists("상의", "후드티", 20.0, 22.0);
        addClothingIfNotExists("하의", "면바지", 20.0, 22.0);
        addClothingIfNotExists("하의", "슬랙스", 20.0, 22.0);
        addClothingIfNotExists("하의", "청바지", 20.0, 22.0);

        // 17~19도
        addClothingIfNotExists("아우터", "얇은 니트", 17.0, 19.0);
        addClothingIfNotExists("아우터", "얇은 가디건", 17.0, 19.0);
        addClothingIfNotExists("아우터", "얇은 재킷", 17.0, 19.0);
        addClothingIfNotExists("아우터", "바람막이", 17.0, 19.0);
        addClothingIfNotExists("상의", "후드티", 17.0, 19.0);
        addClothingIfNotExists("상의", "맨투맨", 17.0, 19.0);
        addClothingIfNotExists("하의", "긴바지", 17.0, 19.0);
        addClothingIfNotExists("하의", "청바지", 17.0, 19.0);
        addClothingIfNotExists("하의", "슬랙스", 17.0, 19.0);
        addClothingIfNotExists("하의", "스키니진", 17.0, 19.0);

        // 12~16도
        addClothingIfNotExists("아우터", "재킷", 12.0, 16.0);
        addClothingIfNotExists("아우터", "가디건", 12.0, 16.0);
        addClothingIfNotExists("아우터", "야상", 12.0, 16.0);
        addClothingIfNotExists("상의", "스웨트 셔츠", 12.0, 16.0);
        addClothingIfNotExists("상의", "맨투맨", 12.0, 16.0);
        addClothingIfNotExists("상의", "셔츠", 12.0, 16.0);
        addClothingIfNotExists("상의", "기모 후드티", 12.0, 16.0);
        addClothingIfNotExists("하의", "청바지", 12.0, 16.0);
        addClothingIfNotExists("하의", "면바지", 12.0, 16.0);
        addClothingIfNotExists("복합", "스타킹", 12.0, 16.0);
        addClothingIfNotExists("복합", "니트", 12.0, 16.0);

        // 9~11도
        addClothingIfNotExists("아우터", "재킷", 9.0, 11.0);
        addClothingIfNotExists("아우터", "야상", 9.0, 11.0);
        addClothingIfNotExists("아우터", "점퍼", 9.0, 11.0);
        addClothingIfNotExists("아우터", "트렌치 코트", 9.0, 11.0);
        addClothingIfNotExists("하의", "청바지", 9.0, 11.0);
        addClothingIfNotExists("하의", "면바지", 9.0, 11.0);
        addClothingIfNotExists("복합", "레이어드 니트", 9.0, 11.0);

        // 5~8도
        addClothingIfNotExists("아우터", "코트", 5.0, 8.0);
        addClothingIfNotExists("아우터", "가죽 재킷", 5.0, 8.0);
        addClothingIfNotExists("하의", "레깅스", 5.0, 8.0);
        addClothingIfNotExists("하의", "청바지", 5.0, 8.0);
        addClothingIfNotExists("하의", "두꺼운 바지", 5.0, 8.0);
        addClothingIfNotExists("복합", "기모 바지", 5.0, 8.0);
        addClothingIfNotExists("복합", "기모 스타킹", 5.0, 8.0);
        addClothingIfNotExists("복합", "스카프", 5.0, 8.0);
        addClothingIfNotExists("복합", "플리스", 5.0, 8.0);
        addClothingIfNotExists("복합", "내복", 5.0, 8.0);

        // 4도 이하
        addClothingIfNotExists("아우터", "패딩", -10.0, 4.0);
        addClothingIfNotExists("아우터", "두꺼운 코트", -10.0, 4.0);
        addClothingIfNotExists("복합", "누빔", -10.0, 4.0);
        addClothingIfNotExists("복합", "내복", -10.0, 4.0);
        addClothingIfNotExists("복합", "목도리", -10.0, 4.0);
        addClothingIfNotExists("복합", "장갑", -10.0, 4.0);
        addClothingIfNotExists("복합", "기모 스타킹", -10.0, 4.0);
        addClothingIfNotExists("복합", "방한용품", -10.0, 4.0);
    }

//    private void addClothingIfNotExists(String category, String itemName, Double minTemp, Double maxTemp) {
//        // 동일한 카테고리, 아이템 이름, 온도 범위가 중복되지 않도록 체크
//        if (!clothingRepository.existsByCategoryAndItemNameAndMinTempAndMaxTemp(category, itemName, minTemp, maxTemp)) {
//            clothingRepository.save(new Clothing(category, itemName, minTemp, maxTemp));
//        } else {
//            // 이미 존재하는 경우, 업데이트 로직 추가
//            Clothing existingClothing = clothingRepository.findByCategoryAndItemName(category, itemName);
//            if (existingClothing != null) {
//                existingClothing.setMinTemp(minTemp);
//                existingClothing.setMaxTemp(maxTemp);
//                clothingRepository.save(existingClothing);
//            }
//        }
//    }

    private void addClothingIfNotExists(String category, String itemName, Double minTemp, Double maxTemp) {
        log.info("Received data -> Category: {}, Item: {}, MinTemp: {}, MaxTemp: {}",
                category, itemName, minTemp, maxTemp);

        if (minTemp == null || maxTemp == null || minTemp > maxTemp) {
            throw new IllegalArgumentException("Invalid temperature range: MinTemp=" + minTemp + ", MaxTemp=" + maxTemp);
        }

        // 동일한 아이템 이름을 가진 데이터 가져오기
        List<Clothing> existingClothingList = clothingRepository.findByCategoryAndItemName(category, itemName);

        boolean isOverlapping = existingClothingList.stream().anyMatch(existing ->
                !(existing.getMaxTemp() < minTemp || existing.getMinTemp() > maxTemp) // 범위가 겹치는 경우
        );

        if (isOverlapping) {
            log.info("Clothing with overlapping range exists: {} - {} ({}°C to {}°C)",
                    category, itemName, minTemp, maxTemp);
        } else {
            Clothing newClothing = new Clothing(category, itemName, minTemp, maxTemp);
            clothingRepository.save(newClothing);
            log.info("Clothing added: {} - {} ({}°C to {}°C)", category, itemName, minTemp, maxTemp);
        }
    }
}