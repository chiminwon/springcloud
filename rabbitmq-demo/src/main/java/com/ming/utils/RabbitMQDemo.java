package com.ming.utils;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.Date;

public class RabbitMQDemo {

    public static void main(String[] args) {

        publisher();
        consumer();
    }

    //推送消息
    public static void publisher() {
        // 创建一个连接
        Connection connection = RabbitConnectionUtils.getRabbitConnection2();
        if (null != connection) {
            try {
                // 创建信道
                Channel channel = connection.createChannel();
                // 声明队列【参数说明：参数一：队列名称，参数二：是否持久化；参数三：是否独占模式；参数四：消费者断开连接时是否删除队列；参数五：消息其他参数】
                channel.queueDeclare(ConfigConstants.QUEUE_NAME, false, false, false, null);
                String content = String.format("Current Time: %s", new Date().getTime());
                // 发送内容【参数说明：参数一：交换机名称；参数二：队列名称，参数三：消息的其他属性-routing headers，此属性为MessageProperties.PERSISTENT_TEXT_PLAIN用于设置纯文本消息存储到硬盘；参数四：消息主体】
                channel.basicPublish("", ConfigConstants.QUEUE_NAME, null, content.getBytes(ConfigConstants.CODE_FORMAT));
                System.out.println("发送消息: " + content);
                // 关闭信道
                channel.close();
                // 关闭连接
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    //消费消息
    public static void consumer() {
        // 创建一个连接
        Connection connection = RabbitConnectionUtils.getRabbitConnection2();
        if (null != connection) {
            try {
                // 创建信道
                Channel channel = connection.createChannel();
                // 声明队列【参数说明：参数一：队列名称，参数二：是否持久化；参数三：是否独占模式；参数四：消费者断开连接时是否删除队列；参数五：消息其他参数】
                channel.queueDeclare(ConfigConstants.QUEUE_NAME, false, false, false, null);
                // 创建订阅器，并接受消息
                channel.basicConsume(ConfigConstants.QUEUE_NAME, false, "", new DefaultConsumer(channel) {
                    @Override
                    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                        // 队列名称
                        String routeKey = envelope.getRoutingKey();
                        String contentType = properties.getContentType();
                        String content = new String(body, ConfigConstants.CODE_FORMAT);
                        System.out.println("routeKey:" + routeKey + " contentType:" + contentType + " 消息正文：" + content);
                        // 手动确认消息【参数说明：参数一：该消息的index；参数二：是否批量应答，true批量确认小于index的消息】
                        channel.basicAck(envelope.getDeliveryTag(), false);
                    }
                });
                // 关闭信道
                //channel.close();
                // 关闭连接
                //connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}
