package org.lingg.activemq.demo1.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import javax.jms.*;

@Service("topicSender")
public class TopicSender {

    @Autowired
    private JmsTemplate jmsTemplate;



    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");

        TopicSender ct = (TopicSender) ctx.getBean("topicSender");

//        ct.jmsTemplate.send(session -> {
//            TextMessage msg = session.createTextMessage("topic spring msg ^^^^^");
//            return msg;
//        });

        ct.jmsTemplate.send((Destination) ctx.getBean("destinationTopic"), new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                TextMessage msg = session.createTextMessage("topic spring msg ^^^^^");
                return msg;
            }
        });
    }
}
