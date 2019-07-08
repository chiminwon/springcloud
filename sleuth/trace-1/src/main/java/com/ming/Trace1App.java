package com.ming;

import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@EnableDiscoveryClient
@SpringBootApplication
public class Trace1App {

    private Logger logger = Logger.getLogger(getClass());

    public static void main(String[] args) {
        SpringApplication.run(Trace1App.class, args);
    }

    @Bean
    @LoadBalanced
    RestTemplate restTemplate(){
        return new RestTemplate();
    }

    @RequestMapping("/trace-1")
    public String trace(){
        logger.info("===call trace-1===");
        return restTemplate().getForEntity("http://trace-2/trace-2", String.class).getBody();
    }
}
