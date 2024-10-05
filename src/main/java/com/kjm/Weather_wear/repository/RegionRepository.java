package com.kjm.Weather_wear.repository;

import com.kjm.Weather_wear.entity.Region;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegionRepository extends JpaRepository<Region, Long> {
}
