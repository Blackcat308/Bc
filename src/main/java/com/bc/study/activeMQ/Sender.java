package com.bc.study.activeMQ;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Created by BlackCat.
 * Date 2017/8/10.
 * Time 17:11
 */
public class Sender {

    private static final int SEND_NUMBER = 5;

    public static void main(String[] args) {
        // ConnectionFactory : 连接工厂，JMS用它创建连接
        ConnectionFactory connectionFactory; //Connection : JMS 客户端到JMS
        //Provider 的连接
        Connection connection  = null;
        Session session; // Session: 一个发送或者接受消息的线程
        Destination destination; // Destiantion ： 消息的目的地：消息发送给谁
        MessageProducer producer; // MessageProducer 消息发送者

        //构造connectionFactory对象实例对象，此处采用ActiveMQ的实现jar
        connectionFactory = new ActiveMQConnectionFactory(
                ActiveMQConnection.DEFAULT_USER,
                ActiveMQConnection.DEFAULT_PASSWORD,
                "tcp://localhost:61616");

        try {
            //构造从工厂得到连接对象
            connection = connectionFactory.createConnection();
            //启动
            connection.start();
            //获取操作连接
            session = connection.createSession(Boolean.TRUE,
                    Session.AUTO_ACKNOWLEDGE);
            //获取session注意参数是一个服务器的queue，须在ActiveMQ的Queues配置
            destination = session.createQueue("FirstQueue");
            //得到消息生成者（发送者）
            producer = session.createProducer(destination);
            //设置不持久化，此处学习，实际根据项目定
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
            //构造消息，此处写死，项目就是参数
            sendMessage(session,producer);
            session.commit();
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

    private static void sendMessage(Session session, MessageProducer producer)
            throws JMSException {
       for (int i = 1; i <= SEND_NUMBER; i++ ){
           TextMessage message =
                   session.createTextMessage("ActiveMQ 发送的消息" + i);
           //发送消息到目的地
           System.out.println("发送消息：" + "ActiveMQ 发送的消息"  + i);
           producer.send(message);
       }
    }
}
