package com.kjm.Weather_wear.service;

import com.kjm.Weather_wear.dto.RegionDTO;
import com.kjm.Weather_wear.entity.Region;
import com.kjm.Weather_wear.repository.RegionRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RegionService {

    private final RegionRepository regionRepository;

    @Transactional
    public void saveRegion(RegionDTO regionDTO) {
        Region region = new Region(
                regionDTO.getId(),
                regionDTO.getParentRegion(),
                regionDTO.getChildRegion(),
                regionDTO.getGrandChildRegion(),
                regionDTO.getNx(),
                regionDTO.getNy()
        );
    }
}