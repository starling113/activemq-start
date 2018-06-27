package org.lingg.activemq.demo1.delay;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ScheduledMessage;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.lingg.activemq.demo1.util.ActiveMQConst;

import javax.jms.*;

public class Sender {

    public static void main(String[] args) throws Exception{
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(ActiveMQConst.brokerURL);
        Connection connection = connectionFactory.createConnection();
        connection.start();

        Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
        Topic topic1 = session.createTopic("MyTopic2");
        MessageProducer producer = session.createProducer(topic1);

    //    for(int i=0; i<3; i++) {
            Message textMessage = session.createTextMessage("DELAY" + 999);

            textMessage.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_DELAY, 5*1000);//延迟5秒后发送
            textMessage.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_PERIOD, 3*1000);//重复投递的时间间隔
            textMessage.setIntProperty(ScheduledMessage.AMQ_SCHEDULED_REPEAT, 4);//重复4次发送

            producer.send(textMessage);
     //   }

        session.commit();
        session.close();
        connection.close();
    }

}
