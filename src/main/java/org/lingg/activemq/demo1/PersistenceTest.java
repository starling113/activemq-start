package org.lingg.activemq.demo1;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.Test;

import javax.jms.*;

public class PersistenceTest {

    @Test
    public void testTopicSender() throws JMSException {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
        Connection connection = connectionFactory.createConnection();


        Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
        Topic topic1 = session.createTopic("topic2");
        MessageProducer producer = session.createProducer(topic1);

        producer.setDeliveryMode(DeliveryMode.PERSISTENT); // 持久
        connection.start();

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
        topicPersistenceReceiver("client111", "name1");
    }
    @Test
    public void testTopicReceiver2() throws Exception{
        topicPersistenceReceiver("client222", "name2");
    }

    private void topicPersistenceReceiver(String clientID, String name) throws JMSException {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");

        Connection connection = connectionFactory.createConnection();
        connection.setClientID(clientID);

        Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
        Topic topic1 = session.createTopic("topic2");
        TopicSubscriber receiver = session.createDurableSubscriber(topic1, name);
        connection.start();

        TextMessage receive = null;
        while ((receive = (TextMessage) receiver.receive(5000L)) != null) {
            System.out.println(clientID+" ====> "+receive.getText());
        }

        session.commit();
        session.close();
        connection.close();
    }
}
