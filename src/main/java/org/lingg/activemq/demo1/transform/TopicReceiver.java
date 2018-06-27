package org.lingg.activemq.demo1.transform;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.lingg.activemq.demo1.util.ActiveMQConst;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Enumeration;
import javax.jms.*;

public class TopicReceiver {

    public static void main(String[] args) throws Exception {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(ActiveMQConst.brokerURL);

        Connection connection = connectionFactory.createConnection();

        connection.start();

        Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
        Topic topic1 = session.createTopic("MyTopic2");

        final MessageConsumer consumer = session.createConsumer(topic1);

        consumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                try {
                    MapMessage msg = (MapMessage) message;

                    Enumeration mapNames = msg.getMapNames();
                    while (mapNames.hasMoreElements()){
                        String k = (String) mapNames.nextElement();
                        System.err.println(k+ " = " + msg.getObject(k));
                    }

                    Enumeration propertyNames = msg.getPropertyNames();
                    while (propertyNames.hasMoreElements()){
                        String key = (String) propertyNames.nextElement();
                        System.out.println(key +" = "+msg.getObjectProperty(key));
                    }

                    msg.acknowledge();

                } catch (Exception err) {
                    err.printStackTrace();
                }
            }
        });


//        session.close();
//        connection.close();
    }
}
