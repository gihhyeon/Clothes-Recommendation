package com.kjm.Weather_wear.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Clothing {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String category;
    private String itemName;
    private Double minTemp;         // 착용 가능한 최소 기온
    private Double maxTemp;         // 착용 가능한 최대 기온

//    public Clothing(String category, String itemName, Double minTemp, Double maxTemp) {
//        this.category = category;
//        this.itemName = itemName;
//        this.minTemp = maxTemp;
//        this.maxTemp = maxTemp;
//    }
}