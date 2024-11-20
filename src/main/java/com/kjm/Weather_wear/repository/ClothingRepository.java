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
    Clothing findByCategoryAndItemName(String category, String itemName); // 중복된 데이터를 조회

    @Query("SELECT c FROM Clothing c WHERE :temperature BETWEEN c.minTemp AND c.maxTemp")
    List<Clothing> findByTemperatureRange(@Param("temp") int temperature);
}