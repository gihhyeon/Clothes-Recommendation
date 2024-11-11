package com.kjm.Weather_wear.service;

import com.kjm.Weather_wear.entity.Clothing;
import com.kjm.Weather_wear.repository.ClothingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ClothingService {

    @Autowired
    private ClothingRepository clothingRepository;

    // Create (중복 확인 후 의류 생성)
    public Clothing createClothing(Clothing clothing) {
        if (isDuplicateClothing(clothing)) {
            throw new RuntimeException("Clothing item already exists.");
        }
        return clothingRepository.save(clothing);
    }

    // Read (모든 의류 조회)
    public List<Clothing> getAllClothing() {
        return clothingRepository.findAll();
    }

    // Read (ID로 의류 조회)
    public Optional<Clothing> getClothingById(Long id) {
        return clothingRepository.findById(id);
    }

    // Update (중복 확인 후 의류 수정)
    public Clothing updateClothing(Long id, Clothing clothingDetails) {
        Clothing clothing = clothingRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Clothing not found with id: " + id));
        
        if (isDuplicateClothing(clothingDetails)) {
            throw new RuntimeException("Duplicate clothing item exists.");
        }

        clothing.setCategory(clothingDetails.getCategory());
        clothing.setItemName(clothingDetails.getItemName());

        return clothingRepository.save(clothing);
    }

    // Delete (의류 삭제)
    public void deleteClothing(Long id) {
        if (!clothingRepository.existsById(id)) {
            throw new RuntimeException("Clothing not found with id: " + id);
        }
        clothingRepository.deleteById(id);
    }

    // 중복 확인 메소드
    private boolean isDuplicateClothing(Clothing clothing) {
        return clothingRepository.existsByCategoryAndItemName(clothing.getCategory(), clothing.getItemName());
    }
}