package com.kjm.Weather_wear.controller;

import com.kjm.Weather_wear.dto.RegionDTO;
import com.kjm.Weather_wear.service.RegionService;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class RegionApiController {

    @Value("${resources.location}")
    private String resourceLocation;

    private final RegionService regionService;

    private final EntityManager em;

    @PostMapping("/region")
    @Transactional
    public ResponseEntity<String> resetRegionList() {
        String fileLocation = resourceLocation + "/init/regionList.csv";
        Path path = Paths.get(fileLocation);
        URI uri = path.toUri();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                new UrlResource(uri).getInputStream()))
        ) {
            String line = br.readLine(); // header 폐기
            while ((line = br.readLine()) != null) {
                String[] splits = line.split(",");
                if (splits.length == 6) {
                    RegionDTO regionDTO = new RegionDTO(
                            Long.parseLong(splits[0]),
                            splits[1],
                            splits[2],
                            splits[3],
                            Integer.parseInt(splits[4]),
                            Integer.parseInt(splits[5])
                    );
                    regionService.saveRegion(regionDTO); // 서비스 메서드 호출
                } else {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("잘못된 CSV 형식");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("파일 처리 오류");
        }
        return ResponseEntity.ok("초기화 성공");
    }
}