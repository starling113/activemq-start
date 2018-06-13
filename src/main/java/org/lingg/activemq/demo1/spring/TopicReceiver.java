package org.lingg.activemq.demo1.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import javax.jms.Destination;

@Service
public class TopicReceiver {

    @Autowired
    private JmsTemplate jmsTemplate;


    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");

        TopicReceiver ct = ctx.getBean(TopicReceiver.class);

        Destination destination = (Destination)ctx.getBean("destinationTopic");
        String msg = (String) ct.jmsTemplate.receiveAndConvert(destination);

        System.out.println("receiver msg : " + msg);
    }
}
