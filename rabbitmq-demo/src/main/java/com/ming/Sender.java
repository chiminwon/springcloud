package com.ming;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Sender {
    private static final String QUEUE_NAME = "demo";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection conn = factory.newConnection();
        System.out.println("Connection Address " + conn.getAddress());
        Channel channel = conn.createChannel();
        System.out.println("Connection Address " + channel.getChannelNumber());
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        String msg = "This is first MSG";
        channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
        System.out.println("[X] sent message " + msg);
        channel.close();
        conn.close();
    }
}
