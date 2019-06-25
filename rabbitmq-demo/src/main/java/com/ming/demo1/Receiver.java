package com.ming.demo1;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Receiver {

    private static final String QUEUE_NAME = "demo";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection conn = factory.newConnection();
        System.out.println("Connection Address " + conn.getAddress());
        Channel channel = conn.createChannel();
        System.out.println("Connection Address " + channel.getChannelNumber());
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        Consumer consumer = new DefaultConsumer(channel){

            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg = new String(body, "UTF-8");
                System.out.println(" [x] Received '" + msg + "'");
            }
        };
        channel.basicConsume(QUEUE_NAME, true, consumer);
        channel.close();
        conn.close();
    }
}
