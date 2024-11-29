package com.kjm.Weather_wear.service;

import com.kjm.Weather_wear.dto.RegionDTO;
import com.kjm.Weather_wear.entity.Region;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RegionService {

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