package com.ming;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@ActiveProfiles("peer1")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProviderTests {

    @Autowired
    public TestRestTemplate restTemplate;

    @Test
    public void callServices() {
        String url = "http://localhost:2001/getServices";
        String result = restTemplate.getForObject(url, String.class);
        System.out.println(result);
    }
}
