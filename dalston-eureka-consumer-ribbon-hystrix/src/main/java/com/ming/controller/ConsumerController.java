package com.ming.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class ConsumerController {

    @Autowired
    DiscoveryClient discoveryClient;

    @Autowired
    ConsumerService consumerSerive;

    @GetMapping("/getServices")
    public String getServices() {
        return "Call the server : " + discoveryClient.getLocalServiceInstance().getHost()
                + ":" + discoveryClient.getLocalServiceInstance().getPort()
                + "\n" + consumerSerive.callProviderGetService();
    }

    @GetMapping("/getHostPort")
    public String getProviderPort() {
        return consumerSerive.callProviderGetHostPort();
    }

    @Service
    class ConsumerService {

        @Autowired
        RestTemplate restTemplate;

        @HystrixCommand(fallbackMethod = "fallback")
        public String callProviderGetService() {
            return restTemplate.getForObject("http://dalston-eureka-provider/getServices", String.class);
        }

        @HystrixCommand(fallbackMethod = "fallback")
        public String callProviderGetHostPort() {
            return restTemplate.getForObject("http://dalston-eureka-provider/getHostPort", String.class);
        }

        public String fallback() {
            return "Service is not available, please contact the admin.";
        }
    }
}
