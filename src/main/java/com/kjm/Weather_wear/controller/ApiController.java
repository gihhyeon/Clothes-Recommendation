//package com.kjm.Weather_wear.controller;
//import com.kjm.Weather_wear.dto.RegionDTO;
//import com.kjm.Weather_wear.entity.Region;
//import com.kjm.Weather_wear.service.RegionService;
//import jakarta.persistence.EntityManager;
//import jakarta.transaction.Transactional;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.core.io.UrlResource;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.net.URI;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//
//@Slf4j
//@RestController
//@RequestMapping("/api")
//@RequiredArgsConstructor
//public class ApiController {

//    @Value("${resources.location}")
//    private String resourceLocation;
//
//    private final EntityManager em;
//
//    @PostMapping("/region")
//    @Transactional
//    public ResponseEntity<String> resetRegionList() {
//        String fileLocation = resourceLocation + "/init/regionList.csv"; // 설정파일에 설정된 경로 뒤에 붙인다.
//        Path path = Paths.get(fileLocation);
//        URI uri = path.toUri();
//
//        try (BufferedReader br = new BufferedReader(new InputStreamReader(
//                new UrlResource(uri).getInputStream()))
//        ) {
//            String line = br.readLine(); // head 폐기
//            while ((line = br.readLine()) != null) {
//                String[] splits = line.split(",");
//                em.persist(new Region(Long.parseLong(splits[0]), splits[1], splits[2], splits[3],
//                        Integer.parseInt(splits[4]), Integer.parseInt(splits[5])));
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("오류 발생");
//        }
//        return ResponseEntity.ok("초기화 성공");
//    }

//    @Value("${resources.location}")
//    private String resourceLocation;
//
//    private final RegionService regionService;
//
//    @PostMapping("/region")
//    @Transactional
//    public ResponseEntity<String> resetRegionList() {
//        String fileLocation = resourceLocation + "/init/regionList.csv"; // 설정파일에 설정된 경로 뒤에 붙인다.
//        Path path = Paths.get(fileLocation);
//        URI uri = path.toUri();
//
//        try (BufferedReader br = new BufferedReader(new InputStreamReader(
//                new UrlResource(uri).getInputStream()))
//        ) {
//            String line = br.readLine(); // head 폐기
//            while ((line = br.readLine()) != null) {
//                String[] splits = line.split(",");
//
//                // CSV 데이터를 DTO로 변환 후 서비스에서 처리
//                RegionDTO regionDTO = new RegionDTO(
//                        Long.parseLong(splits[0]), // ID
//                        splits[1], // parentRegion
//                        splits[2], // childRegion
//                        splits[3], // grandChildRegion
//                        Integer.parseInt(splits[4]), // nx
//                        Integer.parseInt(splits[5]) // ny
//                );
//
//                regionService.saveRegion(regionDTO); // RegionService로 저장
//            }
//        } catch (IOException e) {
//            log.error("파일 처리 중 오류 발생", e);
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("오류 발생");
//        }
//        return ResponseEntity.ok("초기화 성공");
//    }
// }
