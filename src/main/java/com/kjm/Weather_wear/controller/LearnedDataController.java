package com.kjm.Weather_wear.controller;

import com.kjm.Weather_wear.dto.LearnedDataDTO;
import com.kjm.Weather_wear.service.LearnedDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/learned-data")
@RequiredArgsConstructor
public class LearnedDataController {

    private final LearnedDataService learnedDataService;

    @PostMapping
    public ResponseEntity<String> saveLearnedData(@RequestBody LearnedDataDTO learnedDataDTO) {
        try {
            learnedDataService.saveLearnedData(learnedDataDTO);
            return ResponseEntity.ok("Learned data saved successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Failed to save learned data: " + e.getMessage());
        }
    }
}