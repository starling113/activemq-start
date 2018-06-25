package org.lingg.activemq.demo1.dispatchPolicies;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.lingg.activemq.demo1.util.ActiveMQConst;

import javax.jms.*;

public class Sender2 {

    public static void main(String[] args) throws Exception{
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(ActiveMQConst.brokerURL);
        Connection connection = connectionFactory.createConnection();
        connection.start();

        Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
        Topic topic1 = session.createTopic("MyTopic");

        for(int j=0; j<2; j++) {
            MessageProducer producer = session.createProducer(topic1);

            for (int i = 0; i < 3; i++) {
                TextMessage textMessage = session.createTextMessage(j+" hello__2__ " + i);
                producer.send(textMessage);
            }

            session.commit();
        }
        session.close();
        connection.close();
    }

}
