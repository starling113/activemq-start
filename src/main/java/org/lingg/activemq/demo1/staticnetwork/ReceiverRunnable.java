package org.lingg.activemq.demo1.staticnetwork;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class ReceiverRunnable implements Runnable {
    private ConnectionFactory connectionFactory;

    public ReceiverRunnable(ConnectionFactory connectionFactory){
        this.connectionFactory = connectionFactory;
    }

    @Override
    public void run() {

        try {

            Connection connection = connectionFactory.createConnection();

            connection.start();

            Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
            Queue firstQueue = session.createQueue("FirstQueue");
            MessageConsumer consumer = session.createConsumer(firstQueue);

            TextMessage receive = null;
            while ((receive = (TextMessage) consumer.receive(10000L)) != null) {
                session.commit();
                System.out.println(receive.getText());
            }

            session.close();
            connection.close();
        }catch (Exception err){
            err.printStackTrace();
        }
        //System.out.println(Thread.currentThread().getName());
    }
}
