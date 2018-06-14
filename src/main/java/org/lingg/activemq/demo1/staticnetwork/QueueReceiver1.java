package org.lingg.activemq.demo1.staticnetwork;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.Test;
import org.lingg.activemq.demo1.util.ActiveMQConst;

import javax.jms.*;
import java.util.concurrent.TimeUnit;

public class QueueReceiver1 {

    public static void main(String[] args) throws InterruptedException {
        String brokerUrl = ActiveMQConst.brokerURL;


        for (int i = 0; i < 3; i++) {
            ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerUrl);
            new Thread(new ReceiverRunnable(connectionFactory)).start();
            TimeUnit.SECONDS.sleep(1);
        }
    }
















    @Test
    public void testStaticNetwork2() {
        String brokerUrl = ActiveMQConst.brokerURL2;


        for (int i = 0; i < 25; i++) {
            ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerUrl);
            new Thread(new ReceiverRunnable(connectionFactory)).start();
        }
    }

    @Test
    public void test3() {
        Thread t=new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("runnable");
            }
        });

        t.start();

    }

}
