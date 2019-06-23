package com.ming.controller;

import com.ming.domain.Department;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.spring4all.swagger.EnableSwagger2Doc;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@EnableSwagger2Doc
@RestController
@Api(tags = "消费者")
public class ConsumerController {

    @Autowired
    DiscoveryClient discoveryClient;

    @Autowired
    ConsumerService consumerSerive;

    @ApiOperation(value = "获取服务列表", notes = "服务列表")
    @GetMapping("/getServices")
    public String getServices() {
        return "Call the server : " + discoveryClient.getLocalServiceInstance().getHost()
                + ":" + discoveryClient.getLocalServiceInstance().getPort()
                + "\n" + consumerSerive.callProviderGetService();
    }

    @ApiOperation(value = "获取主机端口", notes = "主机端口")
    @GetMapping("/getHostPort")
    public String getProviderPort() {
        return consumerSerive.callProviderGetHostPort();
    }

    @ApiOperation(value = "获取所有部门详细信息", notes = "所有部门详细信息")
    @GetMapping("/getAllDepts")
    public List getProviderAllDepts() {
        return consumerSerive.callProviderAllDepts();
    }

    @ApiOperation(value = "获取部门详细信息", notes = "根据url的deptNo来获取部门详细信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "deptNo", value = "部门编号", required = true, dataType = "Long")
    })
    @GetMapping("/getDeptByNo/{deptNo}")
    public Department getDeptByNo(@PathVariable("deptNo") Long deptNo) {
        return consumerSerive.callProviderDeptNo(deptNo);
    }

    @Service
    class ConsumerService {

        @Autowired
        RestTemplate restTemplate;

        String url = "http://dalston-eureka-provider";

        @HystrixCommand(fallbackMethod = "fallback")
        public String callProviderGetService() {
            return restTemplate.getForObject("http://dalston-eureka-provider/getServices", String.class);
        }

        @HystrixCommand(fallbackMethod = "fallback")
        public String callProviderGetHostPort() {
            return restTemplate.getForObject(url + "/getHostPort", String.class);
        }

        @HystrixCommand(fallbackMethod = "deptFallback")
        public List callProviderAllDepts() {
            return restTemplate.getForObject(url + "/getAllDepts", List.class);
        }

        @HystrixCommand(fallbackMethod = "fallback")
        public Department callProviderDeptNo(Long deptNo) {
            return restTemplate.getForObject(url + "/findDeptByNo/a/{deptNo}", Department.class, deptNo);
        }

        public String fallback() {
            return "Service is not available, please contact the admin.";
        }

        public List deptFallback() {
            List msg = new ArrayList();
            msg.add("Service is not available, please contact the admin.");
            return msg;
        }
    }
}
