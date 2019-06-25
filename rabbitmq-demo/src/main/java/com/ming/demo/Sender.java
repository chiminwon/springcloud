package com.ming.demo;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

//消息发送方
public class Sender {

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(DemoConstants.HOST);
        factory.setPort(DemoConstants.PORT);
        factory.setUsername(DemoConstants.USERNAME);
        factory.setPassword(DemoConstants.PASSWORD);
        Connection conn = factory.newConnection();
        System.out.println("Connection Address: " + conn.getAddress());
        Channel channel = conn.createChannel();
        System.out.println("Channel Number: " + channel.getChannelNumber());
        channel.queueDeclare(DemoConstants.QUEUE_NAME, true, false, false, null);
        String msg = "This is a message";
        while (true){
            channel.basicPublish("", DemoConstants.QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, msg.getBytes());
            System.out.println("[X] sent message: " + msg);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
//        channel.close();
//        conn.close();
    }
}
