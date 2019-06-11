package com.ming.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConsumerController {

    @Autowired
    ConsumerProvider consumerProvider;

    @GetMapping("/getServices")
    public String getServices(){
       return consumerProvider.getService();
    }

    @GetMapping("/getHostPort")
    public String getHostPort(){
        return consumerProvider.getHostPort();
    }
}
