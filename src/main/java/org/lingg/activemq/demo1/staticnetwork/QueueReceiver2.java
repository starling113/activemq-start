package org.lingg.activemq.demo1.staticnetwork;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.Test;
import org.lingg.activemq.demo1.util.ActiveMQConst;

import javax.jms.ConnectionFactory;
import java.util.concurrent.TimeUnit;

public class QueueReceiver2 {

    public static void main(String[] args) throws InterruptedException {

        for (int i = 0; i < 30; i++) {
            new Thread(new ReceiverRunnable(ActiveMQConst.brokerURL2)).start();
            TimeUnit.SECONDS.sleep(1);
        }
    }

}
