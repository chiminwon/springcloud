package com.ming.demo;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

//消息接收方
public class Receiver {

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
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg = new String(body, "UTF-8");
                System.out.println(" [x] Received '" + msg + "'");
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        };
        channel.basicConsume(DemoConstants.QUEUE_NAME, true, consumer);
        //channel.close();
        //conn.close();
    }
}
