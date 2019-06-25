package com.ming.utils;

public class ConfigConstants {

    public static final String HOST = "localhost";
    public static final int PORT = 5672;
    public static final String USERNAME = "guest";
    public static final String PASSWORD = "guest";
    public static final String VHOST = "/";
    public static final String QUEUE_NAME = "queue01";
    public static final String CODE_FORMAT = "UTF-8";
    public static final String EXCHANGE_TYPE_FANOUT = "fanout";
    public static final String EXCHANGE_NAME_FANOUT = "fanout01";
    public static final String ROUTINGKEY = "com.mq.rabbit.error";
    public static final String EXCHANGE_TYPE_TOPIC = "topic";
    public static final String EXCHANGE_NAME_TOPIC = "topic01";

}
