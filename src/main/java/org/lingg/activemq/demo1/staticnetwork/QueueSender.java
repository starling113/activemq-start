package org.lingg.activemq.demo1.staticnetwork;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.lingg.activemq.demo1.util.ActiveMQConst;

import javax.jms.*;

public class QueueSender {

    public static void main(String[] args) throws Exception {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(ActiveMQConst.brokerURL);

        Connection connection = connectionFactory.createConnection();

        connection.start();

        Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
        Queue firstQueue = session.createQueue("FirstQueue");

        MessageProducer producer = session.createProducer(firstQueue);
        for (int i = 0; i < 300; i++) {
            TextMessage message = session.createTextMessage("hello" + i);
            producer.send(message);
        }

        session.commit();
        session.close();
        connection.close();
    }
}
