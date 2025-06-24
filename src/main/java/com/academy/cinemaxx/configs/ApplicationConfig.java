package com.academy.cinemaxx.configs;

import io.minio.MinioClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class ApplicationConfig {

    @Bean
    public MinioClient minioClient(MinioConfig config) {
        MinioClient minioClient = MinioClient.builder()
                .endpoint(config.getUrl())
                .credentials(config.getAccessKey(), config.getAccessSecret())
                .build();
        return minioClient;
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}