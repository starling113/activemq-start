package org.lingg.activemq.demo1.dispatchPolicies;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.lingg.activemq.demo1.util.ActiveMQConst;

import javax.jms.*;

public class TopicReceiver {

    public static void main(String[] args) throws Exception {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(ActiveMQConst.brokerURL);

        new Thread(new MyT(connectionFactory)).start();
        new Thread(new MyT(connectionFactory)).start();
        //new Thread(new MyT(connectionFactory)).start();

    }
}

class MyT implements Runnable{

    private  ConnectionFactory connectionFactory;

    public MyT(ConnectionFactory connectionFactory){
        this.connectionFactory = connectionFactory;
    }

    @Override
    public void run() {
        try {
            Connection connection = connectionFactory.createConnection();

            connection.start();

            Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
            Topic topic1 = session.createTopic("MyTopic");

            for (int i = 0; i < 2; i++) {
                final MessageConsumer consumer = session.createConsumer(topic1);

                consumer.setMessageListener(new MessageListener() {
                    @Override
                    public void onMessage(Message message) {
                        try {
                            TextMessage msg = (TextMessage) message;
                            System.out.println(consumer + "---------->" + msg.getText());

                            session.commit();
                        } catch (Exception err) {
                            err.printStackTrace();
                        }
                    }
                });
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}