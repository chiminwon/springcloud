package com.ming.exchange;

import com.ming.utils.ConfigConstants;
import com.ming.utils.RabbitConnectionUtils;
import com.rabbitmq.client.*;
import sun.security.krb5.Config;

import java.io.IOException;
import java.util.Date;

public class FanoutExchange {

    public static void main(String[] args) {
        publisher();
        consumer();
    }

    public static void publisher() {
        Connection connection = RabbitConnectionUtils.getRabbitConnection();
        if (null != connection) {
            try {
                Channel channel = connection.createChannel();
                // 声明fanout交换器
                channel.exchangeDeclare(ConfigConstants.EXCHANGE_NAME_FANOUT, ConfigConstants.EXCHANGE_TYPE_FANOUT);
                String message = String.format("当前时间：%s", new Date().getTime());
                // 推送内容【参数说明：参数一：交换机名称；参数二：队列名称，参数三：消息的其他属性-路由的headers信息；参数四：消息主体】
                channel.basicPublish(ConfigConstants.EXCHANGE_NAME_FANOUT, "", null, message.getBytes(ConfigConstants.CODE_FORMAT));
                System.out.println("Send Msg: " + message);
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
                // 声明fanout交换器
                channel.exchangeDeclare(ConfigConstants.EXCHANGE_NAME_FANOUT, ConfigConstants.EXCHANGE_TYPE_FANOUT);
                // 声明队列
                String queueName = channel.queueDeclare().getQueue();
                System.out.println("queueName：" + queueName);
                channel.queueBind(queueName, ConfigConstants.EXCHANGE_NAME_FANOUT, "");
                Consumer consumer = new DefaultConsumer(channel) {
                    @Override
                    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                        String content = new String(body, ConfigConstants.CODE_FORMAT);
                        System.out.println("消息正文：" + content);
                        // 手动确认消息【参数说明：参数一：该消息的index；参数二：是否批量应答，true批量确认小于index的消息】
                        channel.basicAck(envelope.getDeliveryTag(), false);
                    }
                };
                // 创建订阅器，并接收消息
                channel.basicConsume(queueName, true, consumer);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

}
