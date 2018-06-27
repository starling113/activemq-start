package org.lingg.activemq.demo1.message.selecter;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.lingg.activemq.demo1.util.ActiveMQConst;

import javax.jms.*;

public class SelectorQueueReceiver {


    public static void main(String[] args) throws Exception {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(ActiveMQConst.brokerURL);

        Connection connection = connectionFactory.createConnection();

        connection.start();

        Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
        Destination firstQueue = session.createQueue("my-queue-1");

        for(int i=0;i<2;i++) {
            final MessageConsumer consumer = session.createConsumer(firstQueue,"age >= 10");

            consumer.setMessageListener(message -> {
                try {
                    System.out.println(consumer+"---->"+((TextMessage) message).getText());
                    session.commit();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            });
        }

//        session.close();
//        connection.close();
    }
}
