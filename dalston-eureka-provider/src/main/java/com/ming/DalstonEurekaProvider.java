package com.ming;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class DalstonEurekaProvider {

    public static void main(String[] args) {
        new SpringApplicationBuilder(DalstonEurekaProvider.class).web(true).run(args);
    }
}
