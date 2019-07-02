package com.ming;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
//这个注解声明了刷新配置的范围，如果使用config配置类的话，就声明到配置类上即可
@RefreshScope
public class TestController {

    @Value("${env}")
    private String env;

    @Value("${message}")
    String message;

    @GetMapping("/print")
    public String getEnv(){
        return env;
    }

    @GetMapping("/hello")
    public String getMessage(){
        return message;
    }
}
