package com.ming.utils;


import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeoutException;

public class RabbitConnectionUtils {

    public static Connection getRabbitConnection() {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(ConfigConstants.HOST);
        factory.setPort(ConfigConstants.PORT);
        factory.setUsername(ConfigConstants.USERNAME);
        factory.setPassword(ConfigConstants.PASSWORD);
        factory.setVirtualHost(ConfigConstants.VHOST);
        Connection connection = null;
        try {
            //创建线程池
            ExecutorService es = Executors.newFixedThreadPool(20);
            connection = factory.newConnection(es);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public static Connection getRabbitConnection2() {
        ConnectionFactory factory = new ConnectionFactory();
        // 连接格式：amqp://userName:password@hostName:portNumber/virtualHost
        String uri = String.format("amqp://%s:%s@%s:%d%s", ConfigConstants.USERNAME, ConfigConstants.PASSWORD, ConfigConstants.HOST, ConfigConstants.PORT, ConfigConstants.VHOST);
        Connection connection = null;
        try {
            factory.setUri(uri);
            factory.setVirtualHost(ConfigConstants.VHOST);
            connection = factory.newConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }
}
