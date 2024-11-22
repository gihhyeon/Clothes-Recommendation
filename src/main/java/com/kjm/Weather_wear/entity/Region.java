package com.kjm.Weather_wear.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "region_table")
public class Region {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "region_id")
    private Long id; // 지역 순번

    @Column(name = "region_parent")
    private String parentRegion; // 시, 도

    @Column(name = "region_child")
    private String childRegion; // 시, 군, 구

    @Column(name = "region_grandchild")
    private String grandChildRegion; // 동, 읍, 면, 리

    private int nx; // x좌표
    private int ny; // y좌표

    // Weather와 1:N 관계 설정
    @OneToMany(mappedBy = "region", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Weather> weatherList = new ArrayList<>();

    public Region(Long id, String parentRegion, String childRegion, String grandChildRegion, int nx, int ny) {
        this.id = id;
        this.parentRegion = parentRegion;
        this.childRegion = childRegion;
        this.grandChildRegion = grandChildRegion;
        this.nx = nx;
        this.ny = ny;
    }

    // Weather 추가 메서드
    public void updateRegionWeather(Weather weather) {
        this.weatherList.add(weather);
        weather.setRegion(this); // 역참조 설정
    }

    @Override
    public String toString() {
        return String.join(" ", parentRegion, childRegion, grandChildRegion);
    }
}
