package com.ming;

import com.ming.rabbit.Sender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = RabbitMQApp.class)
public class RabbitMQAppTests {

    @Autowired
    private Sender sender;

    @Test
    public void hello() throws Exception {
        System.out.println("send msg");
        sender.send();
    }
}