package com.ming.controller;

import com.ming.domain.Department;
import com.ming.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProviderController {

    @Autowired
    DiscoveryClient discoveryClient;

    @Autowired
    Environment environment;

    @Autowired
    DeptService deptService;

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

    @GetMapping("/getAllDepts")
    public List<Department> getAllDepartments() {
        return deptService.findAll();
    }

    @PostMapping(value = "/updateDepartment", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String updateDepartment(@ModelAttribute Department dept) {
        return deptService.updateDepartment(dept);
    }
}
