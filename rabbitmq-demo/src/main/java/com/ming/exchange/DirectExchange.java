package com.ming.exchange;

import com.ming.utils.ConfigConstants;
import com.ming.utils.RabbitConnectionUtils;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.Date;

public class DirectExchange {
    public static void main(String[] args) {

        publisher();
        consumer();
    }

    public static void publisher() {
        Connection connection = RabbitConnectionUtils.getRabbitConnection();
        if (null != connection) {
            try {
                Channel channel = connection.createChannel();
                // 声明队列【参数说明：参数一：队列名称，参数二：是否持久化；参数三：是否独占模式；参数四：消费者断开连接时是否删除队列；参数五：消息其他参数】
                channel.queueDeclare(ConfigConstants.QUEUE_NAME, false, false, false, null);
                String message = String.format("当前时间：%s", new Date().getTime());
                // 推送内容【参数说明：参数一：交换机名称；参数二：队列名称，参数三：消息的其他属性-路由的headers信息；参数四：消息主体】
                channel.basicPublish("", ConfigConstants.QUEUE_NAME, null, message.getBytes(ConfigConstants.CODE_FORMAT));
                channel.close();
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //消费消息
    public static void consumer() {
        // 创建一个连接
        Connection connection = RabbitConnectionUtils.getRabbitConnection();
        if (null != connection) {
            try {
                // 创建信道
                Channel channel = connection.createChannel();
                // 声明队列【参数说明：参数一：队列名称，参数二：是否持久化；参数三：是否独占模式；参数四：消费者断开连接时是否删除队列；参数五：消息其他参数】
                channel.queueDeclare(ConfigConstants.QUEUE_NAME, false, false, false, null);
                // 创建订阅器，并接收消息
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

    public static void singleConsumer() {
        // 创建一个连接
        Connection connection = RabbitConnectionUtils.getRabbitConnection2();
        if (null != connection) {
            try {
                // 创建信道
                Channel channel = connection.createChannel();
                // 声明队列【参数说明：参数一：队列名称，参数二：是否持久化；参数三：是否独占模式；参数四：消费者断开连接时是否删除队列；参数五：消息其他参数】
                channel.queueDeclare(ConfigConstants.QUEUE_NAME, false, false, false, null);
                // 获取单条消息
                GetResponse res = channel.basicGet(ConfigConstants.QUEUE_NAME, false);
                String content = new String(res.getBody(), ConfigConstants.CODE_FORMAT);
                System.out.println("消息正文：" + content);
                //消息确认
                channel.basicAck(res.getEnvelope().getDeliveryTag(), false);
                //消息拒绝
                //参数1：消息的id；参数2：处理消息的方式，如果是true，Rabbib会重新分配这个消息给其他订阅者，如果设置成false的话，Rabbit会把消息发送到一个特殊的“死信”队列，用来存放被拒绝而不重新放入队列的消息
                //channel.basicReject(res.getEnvelope().getDeliveryTag(), true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
