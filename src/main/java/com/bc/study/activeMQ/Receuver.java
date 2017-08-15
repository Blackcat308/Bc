package com.bc.study.activeMQ;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Created by BlackCat.
 * Date 2017/8/10.
 * Time 17:12
 */
public class Receuver {

    public static void main(String[] args) {
        // ConnectionFactory ：连接工厂，JMS 用它创建连接
        ConnectionFactory connectionFactory;
        // Connection ：JMS 客户端到JMS Provider 的连接
        Connection connection = null;
        // Session： 一个发送或接收消息的线程
        Session session;
        // Destination ：消息的目的地;消息发送给谁.
        Destination destination;
        // 消费者，消息接收者
        MessageConsumer consumer;
        connectionFactory =  new ActiveMQConnectionFactory(
                ActiveMQConnection.DEFAULT_USER,
                ActiveMQConnection.DEFAULT_PASSWORD,
                "tcp://localhost:61616");

        try {
            // 构造从工厂得到连接对象
            connection =  connectionFactory.createConnection();
            // 启动
            connection.start();
            // 获取操作连接
            session =  connection.createSession(Boolean.TRUE
            ,Session.AUTO_ACKNOWLEDGE);

            destination = session.createQueue("FirstQueue");
            consumer = session.createConsumer(destination);
            while (true){
                // 设置接收者接收消息的时间，为了便于测试，这里谁定为100s
                TextMessage message = (TextMessage) consumer.receive(100000);
                if (null != message){
                    System.out.println("收到的消息" + message.getText());
                }else {
                    break;
                }
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }finally {
                try {
                    if (null != connection){
                        connection.close();
                    }
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        }
    }

