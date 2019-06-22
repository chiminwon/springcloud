package com.ming;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ZuulTests {

    @Autowired
    public TestRestTemplate restTemplate;

    enum GetMethodNames {
        getHostPort, getServices
    }

    @Test
    public void callServices() {

        String servicesUrl = "http://localhost:6001/api-a/getServices?token={token}";
        String portUrl = "http://localhost:6001/api-a/getHostPort?token={token}";
        Map<String, String> requestParam = new HashMap<>();
        requestParam.put("token", "allen");

        while (true) {
            String serviceResult = restTemplate.getForObject(servicesUrl, String.class, requestParam);
            System.out.println("getServices : " + serviceResult);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            String portResult = restTemplate.getForObject(portUrl, String.class, requestParam);
            System.out.println("getHostPort : " + portResult);
        }
    }
}
