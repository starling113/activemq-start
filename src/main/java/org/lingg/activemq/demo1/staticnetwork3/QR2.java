package org.lingg.activemq.demo1.staticnetwork3;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.lingg.activemq.demo1.util.ActiveMQConst;

import javax.jms.*;

public class QR2 {


    public static void main(String[] args) throws Exception {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(ActiveMQConst.brokerURL);
        Connection connection = connectionFactory.createConnection();

        connection.start();

        Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
        Queue firstQueue = session.createQueue("Consumer.B.VirtualTopic.Orders");

        for (int i = 0; i < 2 ; i++){
            MessageConsumer consumer = session.createConsumer(firstQueue);

            int finalI = i;
            consumer.setMessageListener(new MessageListener() {
                @Override
                public void onMessage(Message message) {
                    try {
                        TextMessage msg = (TextMessage) message;
                        System.out.println(finalI +"------" +msg.getText());
                        session.commit();

                        Thread.sleep(1000L);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }



    }

}
