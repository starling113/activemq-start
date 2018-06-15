package org.lingg.activemq.demo1.failover;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.lingg.activemq.demo1.util.ActiveMQConst;

import javax.jms.*;

public class FailOverTest {
    // 容错链接
    public static void main(String[] args) throws Exception {

        //randomize 是否随机访问
        String failOverURL = "failover:("+ActiveMQConst.brokerURL+","+ActiveMQConst.brokerURL2+")?randomize=false";

        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(failOverURL);


        Connection connection = connectionFactory.createConnection();

        connection.start();

        Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
        Queue firstQueue = session.createQueue("FirstQueue");

        MessageProducer producer = session.createProducer(firstQueue);
        for (int i = 0; i < 1; i++) {
            System.out.println(connection);
            TextMessage message = session.createTextMessage("hello" + i);
            producer.send(message);
        }

        session.commit();
        session.close();
        connection.close();
    }
}
