//package com.kjm.Weather_wear.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true) // @PreAuthorize 어노테이션 활성화
//public class SecurityConfig {
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//            .csrf().disable()
//            .authorizeRequests()
//                .antMatchers("/api/clothing/**").hasRole("ADMIN") // ADMIN만 접근 가능
//                .anyRequest().authenticated()
//            .and()
//            .httpBasic(); // 기본 인증 방식 사용 (예시)
//
//        return http.build();
//    }
//}