package com.kjm.Weather_wear.service;

import com.kjm.Weather_wear.dto.RegionDTO;
import com.kjm.Weather_wear.entity.Region;
import com.kjm.Weather_wear.repository.RegionRepository;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
public class RegionService {

    @Autowired
    private RegionRepository regionRepository;

    // Relative path to the CSV file
    private static final String CSV_FILE_PATH = "storage/init/regionList.csv";

    public void saveRegionDataFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(Paths.get(CSV_FILE_PATH).toFile(), StandardCharsets.UTF_8));
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader())) {

            for (CSVRecord record : csvParser) {
                Region region = new Region(
                        Long.parseLong(record.get("region_id")),
                        record.get("region_parent"),
                        record.get("region_child"),
                        record.get("region_grandchild"),
                        Integer.parseInt(record.get("nx")),
                        Integer.parseInt(record.get("ny"))
                );
                regionRepository.save(region);
            }

        } catch (Exception e) {
            throw new RuntimeException("Error reading CSV file: " + e.getMessage());
        }
    }

    public List<RegionDTO> getAllRegions() {
        List<Region> regions = regionRepository.findAll();
        List<RegionDTO> regionDtos = new ArrayList<>();

        for (Region region : regions) {
            regionDtos.add(new RegionDTO(
                    region.getId(),
                    region.getParentRegion(),
                    region.getChildRegion(),
                    region.getGrandChildRegion(),
                    region.getNx(),
                    region.getNy(),
                    region.getWeather()
            ));
        }

        return regionDtos;
    }
}