package com.ming.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProviderController {

    @Autowired
    DiscoveryClient discoveryClient;

    @Autowired
    Environment environment;

    @GetMapping("/getServices")
    public String getServices() {
        String services = "Call the provider: " + discoveryClient.getLocalServiceInstance().getHost()
                + ":" + discoveryClient.getLocalServiceInstance().getPort()
                + " " + discoveryClient.getServices();
        System.out.println(services);
        return services;
    }

    @GetMapping("/getHostPort")
    public String getHostPort() {
        String port = environment.getProperty("local.server.port");
        return "Call Provider Host Port is :: " + port;
    }
}
