package org.lingg.activemq.demo1.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import javax.jms.TextMessage;

@Service("QueueSender")
public class QueueSender {

    @Autowired
    private JmsTemplate jmsTemplate;

    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");

        QueueSender ct = (QueueSender) ctx.getBean("QueueSender");

        ct.jmsTemplate.send(session -> {
            TextMessage msg = session.createTextMessage("spring msg ^^^^^");
            return msg;
        });
    }
}
