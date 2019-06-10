package com.ming;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.turbine.stream.EnableTurbineStream;

@EnableDiscoveryClient
@EnableTurbineStream
@SpringBootApplication
public class DalstonTurbineAmqpApp {
    public static void main(String[] args) {
        SpringApplication.run(DalstonTurbineAmqpApp.class, args);
    }
}
