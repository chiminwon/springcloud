package com.ming.transaction;

import com.ming.utils.ConfigConstants;
import com.ming.utils.RabbitConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.MessageProperties;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeoutException;

public class RabbitTransaction
{
    public static void main(String[] args) {
        testTransaction();
    }

    private static void testTransaction(){
        Connection connection = RabbitConnectionUtils.getRabbitConnection();
        if (null != connection) {
            Channel channel = null;
            try {
                channel = connection.createChannel();
                // 声明队列【参数说明：参数一：队列名称，参数二：是否持久化；参数三：是否独占模式；参数四：消费者断开连接时是否删除队列；参数五：消息其他参数】
                channel.queueDeclare(ConfigConstants.QUEUE_NAME, true, false, false, null);
                String message = String.format("当前时间：%s", new Date().getTime());
                try {
                    //声明事务
                    channel.txSelect();
                    // 推送内容【参数说明：参数一：交换机名称；参数二：队列名称，参数三：消息的其他属性-路由的headers信息；参数四：消息主体】
                    channel.basicPublish("", ConfigConstants.QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes(ConfigConstants.CODE_FORMAT));
                    channel.txCommit();
                } catch (Exception e) {
                   channel.txRollback();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    channel.close();
                    connection.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (TimeoutException e) {
                    e.printStackTrace();
                }

            }
        }
    }
}
