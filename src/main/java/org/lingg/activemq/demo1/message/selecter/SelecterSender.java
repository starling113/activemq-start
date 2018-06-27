package org.lingg.activemq.demo1.message.selecter;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQMessageProducer;
import org.lingg.activemq.demo1.util.ActiveMQConst;

import javax.jms.*;

public class SelecterSender {

    public static void main(String[] args) throws Exception {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(ActiveMQConst.brokerURL);
        Connection connection = connectionFactory.createConnection();
        connection.start();

        Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
        Destination queue1 = session.createQueue("my-queue-1");
        ActiveMQMessageProducer producer = (ActiveMQMessageProducer) session.createProducer(queue1);

        for (int i = 0; i < 10; i++) {
            Message msg1 = session.createTextMessage("group——AAA——msg" + i);
            msg1.setIntProperty("age",10);
            msg1.setStringProperty("JMSXGroupID","group1");//消息分组，一组消息只会选择一个消费者来消费
            producer.send(msg1);

//            Message msg2 = session.createTextMessage("group——BBB——msg" + i);
//            msg2.setStringProperty("JMSXGroupID","group2");
//            producer.send(msg2);
        }
        session.commit();
        session.close();
        connection.close();
    }
}


// 消息分组和选择器配合使用可以一定程度上固定消息到指定的消费者（生产者需要指定有哪些消费者；某个消费者挂了，生产者消息没有消费者了，消息会积压）