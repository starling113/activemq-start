package org.lingg.activemq.demo1.start;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.lingg.activemq.demo1.util.ActiveMQConst;

import javax.jms.*;

public class QueueSenderMapMessage {
    public static void main(String[] args) throws Exception {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(ActiveMQConst.brokerURL);

        Connection connection = connectionFactory.createConnection();

        connection.start();

        Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
        Queue firstQueue = session.createQueue("FirstQueue");

        MessageProducer producer = session.createProducer(firstQueue);
        for (int i = 0; i < 5; i++) {
            MapMessage mapMessage = session.createMapMessage();
            mapMessage.setStringProperty("aaa"+i, "aaa_val"+i);
            mapMessage.setString("bbb"+i, "bbb_val"+i);

            producer.send(mapMessage);
        }

        session.commit();
        session.close();
        connection.close();
    }
}
