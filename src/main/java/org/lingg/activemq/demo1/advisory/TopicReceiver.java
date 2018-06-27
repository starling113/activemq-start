package org.lingg.activemq.demo1.advisory;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.advisory.AdvisorySupport;
import org.apache.activemq.command.ActiveMQTopic;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.lingg.activemq.demo1.util.ActiveMQConst;

import javax.jms.*;

public class TopicReceiver {

    public static void main(String[] args) throws Exception {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(ActiveMQConst.brokerURL);

        Connection connection = connectionFactory.createConnection();

        connection.start();

        Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
        Topic topic1 = session.createTopic("MyTopic2");

//        Topic topic1 = session.createTopic("ActiveMQ.Advisory.Consumer.Topic.MyTopic");
        ActiveMQTopic producerAdvisoryTopic = AdvisorySupport.getProducerAdvisoryTopic(topic1);

        System.out.println(producerAdvisoryTopic.getTopicName());

        final MessageConsumer consumer = session.createConsumer(producerAdvisoryTopic);

        consumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                try {
                    //TextMessage msg = (TextMessage) message;
                    //System.out.println(consumer + "---------->" + msg.getText());
                    System.out.println(ToStringBuilder.reflectionToString(message));

                    session.commit();
                } catch (Exception err) {
                    err.printStackTrace();
                }
            }
        });
    }
}
