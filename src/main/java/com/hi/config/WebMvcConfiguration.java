//package com.hi.config;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.converter.HttpMessageConverter;
//import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
//
//import java.util.List;
//
//@Configuration
//public class WebMvcConfiguration extends WebMvcConfigurationSupport {
//
//    public WebMvcConfiguration() {
//        super();
//    }
//
//    @Override
//    public void configureMessageConverters(List<HttpMessageConverter<?>> converters)
//    {
//        converters.add(new MappingJackson2HttpMessageConverter(customObjectMapper()));
//    }
//
//    @Bean
//    public ObjectMapper customObjectMapper() {
//        return new CustomObjectMapper();
//    }
//
//}
