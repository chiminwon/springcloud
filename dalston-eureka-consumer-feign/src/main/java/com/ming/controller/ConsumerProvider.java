package com.ming.controller;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "dalston-eureka-provider")
public interface ConsumerProvider {

    @GetMapping(value = "/getServices")
    String getService();

    @GetMapping(value = "/getHostPort")
    String getHostPort();
}
