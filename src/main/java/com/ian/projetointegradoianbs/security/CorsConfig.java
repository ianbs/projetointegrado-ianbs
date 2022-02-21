package com.ian.projetointegradoianbs.security;

import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins("*").allowedMethods("GET", "POST", "PUT",
                "DELETE", "OPTIONS", "HEAD", "TRACE", "CONNECT").allowedHeaders("*").allowCredentials(true)
                .maxAge(86000);
    }

}
