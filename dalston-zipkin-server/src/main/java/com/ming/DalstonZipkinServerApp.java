package com.ming;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import zipkin.server.EnableZipkinServer;

@EnableZipkinServer
@SpringBootApplication
public class DalstonZipkinServerApp {
    public static void main(String[] args) {
        SpringApplication.run(DalstonZipkinServerApp.class, args);
    }
}
