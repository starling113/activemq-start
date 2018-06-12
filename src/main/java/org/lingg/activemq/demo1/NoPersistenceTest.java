package org.lingg.activemq.demo1;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.Test;

import javax.jms.*;

public class NoPersistenceTest {

    @Test
    public void testTopicSender() throws JMSException {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
        Connection connection = connectionFactory.createConnection();
        connection.start();

        Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
        Topic topic1 = session.createTopic("topic1");
        MessageProducer producer = session.createProducer(topic1);

        for(int i=0; i<3; i++) {
            TextMessage textMessage = session.createTextMessage("hello" + i);
            producer.send(textMessage);
        }

        session.commit();
        session.close();
        connection.close();
    }

    @Test
    public void testTopicReceiver() throws Exception{
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");

        Connection connection = connectionFactory.createConnection();

        connection.start();

        Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
        Topic topic1 = session.createTopic("topic1");
        MessageConsumer consumer = session.createConsumer(topic1);
        TextMessage receive = null;
        while ((receive = (TextMessage) consumer.receive()) != null) {
            session.commit();
            System.out.println(receive.getText());
        }

        session.close();
        connection.close();
    }
}
