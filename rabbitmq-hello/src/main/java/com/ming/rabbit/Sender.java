package com.ming.rabbit;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class Sender {

    @Autowired
    AmqpTemplate amqpTemplate;

    public void send() {
        String context = "Hello " + new Date();
        System.out.println(context);
        amqpTemplate.convertAndSend("helloQueue", context);
    }
}
