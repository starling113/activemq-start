package org.lingg.activemq.demo1;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Enumeration;
import javax.jms.*;

public class QueueReceiverMapMessage {

    public static void main(String[] args) throws Exception {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://127.0.0.1:61616");

        Connection connection = connectionFactory.createConnection();

        connection.start();

        Enumeration jmsxPropertyNames = connection.getMetaData().getJMSXPropertyNames();
        while (jmsxPropertyNames.hasMoreElements()){
            System.out.println(jmsxPropertyNames.nextElement());
        }

        System.out.println("========================");

        Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
        Queue firstQueue = session.createQueue("FirstQueue");
        MessageConsumer consumer = session.createConsumer(firstQueue);
        MapMessage receive = null;
        while ((receive = (MapMessage) consumer.receive(1000L)) != null) {
            session.commit();

            Enumeration mapNames = receive.getMapNames();
            while (mapNames.hasMoreElements()){
                String k = (String) mapNames.nextElement();
                System.err.println(k+ " = " + receive.getObject(k));
            }

            Enumeration propertyNames = receive.getPropertyNames();
            while (propertyNames.hasMoreElements()){
                String key = (String) propertyNames.nextElement();
                System.out.println(key +" = "+receive.getObjectProperty(key));
            }

            System.out.println("+++++++++++++++++++++++");

        }

        session.close();
        connection.close();
    }
}
