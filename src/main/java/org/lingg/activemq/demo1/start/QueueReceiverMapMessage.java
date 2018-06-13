package org.lingg.activemq.demo1.start;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.lingg.activemq.demo1.util.ActiveMQConst;

import java.util.Enumeration;
import javax.jms.*;

public class QueueReceiverMapMessage {

    public static void main(String[] args) throws Exception {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(ActiveMQConst.brokerURL);

        Connection connection = connectionFactory.createConnection();

        connection.start();

        Enumeration jmsxPropertyNames = connection.getMetaData().getJMSXPropertyNames();
        while (jmsxPropertyNames.hasMoreElements()){
            System.out.println(jmsxPropertyNames.nextElement());
        }

        System.out.println("========================");

        Session session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
        Queue firstQueue = session.createQueue("FirstQueue");
        MessageConsumer consumer = session.createConsumer(firstQueue);
        MapMessage receive = null;
        while ((receive = (MapMessage) consumer.receive(1000L)) != null) {
            //session.commit();

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

            receive.acknowledge();

            System.out.println("+++++++++++++++++++++++");

        }

        session.close();
        connection.close();
    }
}
