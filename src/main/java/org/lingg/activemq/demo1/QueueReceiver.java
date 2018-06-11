package org.lingg.activemq.demo1;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class QueueReceiver {

    public static void main(String[] args) throws Exception {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://127.0.0.1:61616");

        Connection connection = connectionFactory.createConnection();

        connection.start();

        Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
        Queue firstQueue = session.createQueue("FirstQueue");
        MessageConsumer consumer = session.createConsumer(firstQueue);
        TextMessage receive = null;
        while ((receive = (TextMessage) consumer.receive(1000L)) != null) {
            session.commit();
//        System.out.println(ToStringBuilder.reflectionToString(receive.getText(), ToStringStyle.MULTI_LINE_STYLE));
            System.out.println(receive.getText());
        }

        session.close();
        connection.close();
    }
}
