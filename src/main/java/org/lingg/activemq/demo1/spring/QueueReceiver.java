package org.lingg.activemq.demo1.spring;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import java.util.Iterator;

@Service
public class QueueReceiver {

    @Autowired
    private JmsTemplate jmsTemplate;

    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
//        QueueReceiver1 ct = (QueueReceiver1)ctx.getBean("QueueReceiver1");
        QueueReceiver ct = ctx.getBean(QueueReceiver.class);
//
//        Iterator<String> beanNamesIterator = ((ClassPathXmlApplicationContext) ctx).getBeanFactory().getBeanNamesIterator();
//        while (beanNamesIterator.hasNext()){
//            System.out.println(beanNamesIterator.next());
//        }

//        Message receive = ct.jmsTemplate.receive();
        String msg = (String) ct.jmsTemplate.receiveAndConvert();

        System.out.println("receiver msg : " + msg);
    }
}
