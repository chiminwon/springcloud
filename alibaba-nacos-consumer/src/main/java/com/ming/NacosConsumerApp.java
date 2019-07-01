package com.ming;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@EnableDiscoveryClient
@SpringBootApplication
public class NacosConsumerApp {
    public static void main(String[] args) {
        SpringApplication.run(NacosConsumerApp.class, args);
    }

    @Slf4j
    @RestController
    static class TestController {

        /*@Autowired
        LoadBalancerClient loadBalancerClient;

        @GetMapping("/consumer")
        public String consumer(){
            ServiceInstance serviceInstance = loadBalancerClient.choose("alibaba-nacos-client");
            String url = serviceInstance.getUri()+"/hello?name="+"Allen";
            RestTemplate restTemplate = new RestTemplate();
            String result = restTemplate.getForObject(url, String.class);
            return "Invoke : " + url + ", return : " + result;
        }*/

        @Autowired
        RestTemplate restTemplate;

        public String test() {
            String result = restTemplate.getForObject("http://alibaba-nacos-client/hello?name=Allen", String.class);
            return "Retrun: " + result;
        }
    }

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
