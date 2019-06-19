package com.ming.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class OAuth2ResourceApp {

    public static void main(String[] args) {
        SpringApplication.run(OAuth2ResourceApp.class, args);
    }

}