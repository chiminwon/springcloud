package com.ming;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class DalstonConfigClientApp {
    public static void main(String[] args) {
        SpringApplication.run(DalstonConfigClientApp.class, args);
    }
}
