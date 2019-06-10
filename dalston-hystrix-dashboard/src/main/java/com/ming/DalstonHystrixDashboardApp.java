package com.ming;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

@EnableDiscoveryClient
@EnableHystrixDashboard
@SpringBootApplication
public class DalstonHystrixDashboardApp {

    public static void main(String[] args) {
        new SpringApplicationBuilder(DalstonHystrixDashboardApp.class).web(true).run(args);
    }
}
