package com.ming;

import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableDiscoveryClient
@SpringBootApplication
public class Trace2App {

    Logger logger = Logger.getLogger(getClass());

    public static void main(String[] args) {
        SpringApplication.run(Trace2App.class, args);
    }

    @RequestMapping("/trace-2")
    public String trace(){
        logger.info("===<call trace-2>===");
        return "Trace2";
    }
}
