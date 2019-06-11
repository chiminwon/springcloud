package com.ming;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class DalstonConsulProviderApp {
    public static void main(String[] args) {
        SpringApplication.run(DalstonConsulProviderApp.class, args);
    }
}
