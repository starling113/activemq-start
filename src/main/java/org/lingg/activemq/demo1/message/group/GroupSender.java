package org.lingg.activemq.demo1.message.group;

import jdk.internal.org.objectweb.asm.tree.IntInsnNode;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQMessageProducer;
import org.apache.activemq.MessageTransformer;
import org.lingg.activemq.demo1.util.ActiveMQConst;

import javax.jms.*;

public class GroupSender {

    public static void main(String[] args) throws Exception {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(ActiveMQConst.brokerURL);
        Connection connection = connectionFactory.createConnection();
        connection.start();

        Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
        Destination queue1 = session.createQueue("my-queue-1");
        ActiveMQMessageProducer producer = (ActiveMQMessageProducer) session.createProducer(queue1);

        for (int i = 0; i < 10; i++) {
            Message msg1 = session.createTextMessage("group——AAA——msg" + i);
            msg1.setStringProperty("JMSXGroupID","group1");//消息分组，一组消息只会选择一个消费者来消费
            producer.send(msg1);

//            Message msg2 = session.createTextMessage("group——BBB——msg" + i);
//            msg2.setStringProperty("JMSXGroupID","group2");
//            producer.send(msg2);
        }
        session.commit();
        session.close();
        connection.close();
    }
}
