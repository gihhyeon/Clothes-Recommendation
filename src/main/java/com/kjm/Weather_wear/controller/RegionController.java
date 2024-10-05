package com.kjm.Weather_wear.controller;

import com.kjm.Weather_wear.dto.RegionDTO;
import com.kjm.Weather_wear.service.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/regions")
public class RegionController {

    @Autowired
    private RegionService regionService;

    // Endpoint to load CSV data from relative path
    @PostMapping("/load")
    public String loadRegionDataFromFile() {
        regionService.saveRegionDataFromFile();
        return "CSV data loaded successfully from file!";
    }

    // Endpoint to get all regions
    @GetMapping
    public List<RegionDTO> getAllRegions() {
        return regionService.getAllRegions();
    }
}