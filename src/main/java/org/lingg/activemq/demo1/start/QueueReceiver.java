package org.lingg.activemq.demo1.start;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.lingg.activemq.demo1.util.ActiveMQConst;

import javax.jms.*;

public class QueueReceiver {


    public static void main(String[] args) throws Exception {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(ActiveMQConst.brokerURL);

        Connection connection = connectionFactory.createConnection();

        connection.start();

        Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
        Queue firstQueue = session.createQueue("FirstQueue");
        MessageConsumer consumer = session.createConsumer(firstQueue);

//        consumer.setMessageListener(new MessageListener() {
//            @Override
//            public void onMessage(Message message) {
//                try {
//                    System.out.println(((TextMessage)message).getText());
//                } catch (JMSException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
        TextMessage receive = null;
        while ((receive = (TextMessage) consumer.receive(1000L)) != null) {
            session.commit();
            System.out.println(receive.getText());
        }

        session.close();
        connection.close();
    }
}
