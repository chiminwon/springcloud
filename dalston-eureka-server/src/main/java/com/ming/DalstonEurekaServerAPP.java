package com.ming;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

import java.util.HashMap;
import java.util.Map;

@EnableEurekaServer
@SpringBootApplication
public class DalstonEurekaServerAPP {
    public static void main(String[] args) {
        SpringApplication.run(DalstonEurekaServerAPP.class, args);
    }
}
