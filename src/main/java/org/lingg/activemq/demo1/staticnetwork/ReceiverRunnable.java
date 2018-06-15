package org.lingg.activemq.demo1.staticnetwork;

import org.apache.activemq.ActiveMQConnectionFactory;

import java.util.concurrent.TimeUnit;
import javax.jms.*;

public class ReceiverRunnable implements Runnable {
    private String brokerUrl;

    public ReceiverRunnable(String brokerUrl){
        this.brokerUrl = brokerUrl;
    }

    @Override
    public void run() {

        try {
            ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerUrl);
            Connection connection = connectionFactory.createConnection();

            connection.start();

            Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
            Queue firstQueue = session.createQueue("FirstQueue");
            MessageConsumer consumer = session.createConsumer(firstQueue);

            consumer.setMessageListener(new MessageListener() {
                @Override
                public void onMessage(Message message) {
                    //System.out.println(message);
                    try {
                        TextMessage msg = (TextMessage) message;
                        System.out.println(msg.getText());
                        session.commit();
                        session.close();
                        connection.close();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });


        }catch (Exception err){
            err.printStackTrace();
        }
    }
}
