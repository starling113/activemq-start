package org.lingg.activemq.demo1.transform;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQMessageProducer;
import org.apache.activemq.MessageTransformer;
import org.apache.activemq.ScheduledMessage;
import org.lingg.activemq.demo1.util.ActiveMQConst;

import javax.jms.*;

public class Sender {

    public static void main(String[] args) throws Exception{
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(ActiveMQConst.brokerURL);
        Connection connection = connectionFactory.createConnection();
        connection.start();

        Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
        Topic topic1 = session.createTopic("MyTopic2");
        ActiveMQMessageProducer producer = (ActiveMQMessageProducer) session.createProducer(topic1);

            Message textMessage = session.createTextMessage("DELAY" + 999);

            //消息转换
            producer.setTransformer(new MessageTransformer() {
                @Override
                public Message producerTransform(Session session, MessageProducer producer, Message message) throws JMSException {

                    TextMessage txtMsg = (TextMessage) message;
                    MapMessage mapMessage = session.createMapMessage();
                    mapMessage.setString(txtMsg.getText()+" kkk", txtMsg.getText()+" convert to map message");
                    return mapMessage;
                }

                @Override
                public Message consumerTransform(Session session, MessageConsumer consumer, Message message) throws JMSException {
                    return null;
                }
            });

            producer.send(textMessage);

        session.commit();
        session.close();
        connection.close();
    }

}
