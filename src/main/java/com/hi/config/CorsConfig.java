package com.hi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true); //응답할 때 json 을 자바스크립트에서 처리할 수 있게 하는 것
        config.addAllowedOrigin("*"); //모든 ip에 응답을 허용
        config.addAllowedHeader("*"); //모든 헤더에 응답을 허용
        config.addAllowedMethod("*"); //모든 post, get, put, delete, patch 요청을 허용

        source.registerCorsConfiguration("/api/**", config);
        return new CorsFilter(source);
    }
}
