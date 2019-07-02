package com.ming;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;

@EnableDiscoveryClient
@SpringBootApplication
public class DalstonConfigClientApp {
    public static void main(String[] args) {
        SpringApplication.run(DalstonConfigClientApp.class, args);
    }
}
