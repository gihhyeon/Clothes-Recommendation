package com.kjm.Weather_wear.repository;

import com.kjm.Weather_wear.entity.Clothing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClothingRepository extends JpaRepository<Clothing, Long> {
    boolean existsByCategoryAndItemName(String category, String itemName);
    //Clothing findByCategoryAndItemName(String category, String itemName); // 중복된 데이터를 조회
    boolean existsByCategoryAndItemNameAndMinTempAndMaxTemp(String category, String itemName, Double minTemp, Double maxTemp);

    // 기존 데이터 가져오기 (중복 제거)
    List<Clothing> findByCategoryAndItemName(String category, String itemName);
    //@Query("SELECT c FROM Clothing c WHERE :temp BETWEEN c.minTemp AND c.maxTemp")
    //List<Clothing> findByTemperatureRange(@Param("temp") double temp);

    @Query("SELECT c FROM Clothing c WHERE c.minTemp <= :temp AND c.maxTemp >= :temp")
    List<Clothing> findAllByTemperatureRange(@Param("temp") double temp);
}