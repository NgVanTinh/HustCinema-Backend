package com.hustcinema.backend.config.web;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000")
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowCredentials(true)
                .allowedHeaders("*");

        // Cấu hình CORS riêng cho các đường dẫn không yêu cầu thông tin xác thực
        registry.addMapping("/api/movie/**")
                .allowedOrigins("*")
                .allowedMethods("GET")
                .allowCredentials(false)
                .allowedHeaders("*");
        registry.addMapping("/api/schedule/**")
                .allowedOrigins("*")
                .allowedMethods("GET")
                .allowCredentials(false)
                .allowedHeaders("*");
        registry.addMapping("/api/seat/**")
                .allowedOrigins("*")
                .allowedMethods("GET")
                .allowCredentials(false)
                .allowedHeaders("*");
        registry.addMapping("/api/user/**")
                .allowedOrigins("*")
                .allowedMethods("POST")
                .allowCredentials(false)
                .allowedHeaders("*");
    }

}
