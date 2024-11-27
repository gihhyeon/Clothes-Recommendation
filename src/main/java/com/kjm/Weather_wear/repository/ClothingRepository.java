package com.kjm.Weather_wear.repository;

import com.kjm.Weather_wear.entity.Clothing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClothingRepository extends JpaRepository<Clothing, Long> {
    // 특정 온도 범위에 해당하는 의류 목록 조회
    @Query("SELECT c FROM Clothing c WHERE c.minTemp <= :temp AND c.maxTemp >= :temp")
    List<Clothing> findAllByTemperatureRange(@Param("temp") double temp);

    /**
     * 특정 카테고리와 아이템 이름, 온도 범위에 해당하는 데이터가 존재하는지 확인
     *
     * @param category 카테고리
     * @param itemName 아이템 이름
     * @param minTemp 최소 온도
     * @param maxTemp 최대 온도
     * @return 데이터 존재 여부
     */
    boolean existsByCategoryAndItemNameAndMinTempAndMaxTemp(String category, String itemName, Double minTemp, Double maxTemp);

    /**
     * 특정 카테고리와 아이템 이름으로 데이터를 조회
     *
     * @param category 카테고리
     * @param itemName 아이템 이름
     * @return Clothing 객체
     */
    Clothing findByCategoryAndItemName(String category, String itemName);


}