package com.kjm.Weather_wear.repository;

import com.kjm.Weather_wear.entity.Clothing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClothingRepository extends JpaRepository<Clothing, Long> {
    
    // 중복 확인을 위한 메소드
    boolean existsByCategoryAndItemName(String category, String itemName);
}