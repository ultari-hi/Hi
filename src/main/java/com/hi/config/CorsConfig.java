package com.hi.config;

import org.apache.catalina.filters.CorsFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true); //응답할 때 json을 자카스크립트에서 처리할 수 있게 하는 것
        config.addAllowedOrigin("*"); //모든 ip에 응답을 허용
        config.addAllowedHeader("*"); //모든 header에 응답을 허용
        config.addAllowedMethod("*"); //모든 post, get, put, delete, patch 요청을 허용
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter();
    }
}
