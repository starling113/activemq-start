package org.lingg.activemq.demo1.broker;

import org.apache.activemq.broker.BrokerFactory;
import org.apache.activemq.broker.BrokerService;
import org.junit.Test;
import org.lingg.activemq.demo1.util.ActiveMQConst;

import java.net.URI;

public class BrokerTest {

    private static String topicName = "topic1";

    // @Test 启动后会立刻shutdown  ？？？？
    public static void main2(String[] args) throws Exception {
        BrokerService broker = new BrokerService();
        // 启用broker的JMX监控功能
        broker.setUseJmx(true);
        // 设置broker名字
        broker.setBrokerName("MyBroker");
        // 是否使用持久化
        broker.setPersistent(false);
        // 添加连接协议，地址
        broker.addConnector(ActiveMQConst.brokerURL);
        broker.start();
    }

    public static void main(String[] args) throws Exception {
        String uri = "properties:broker.properties";
        BrokerService broker = BrokerFactory.createBroker(new URI(uri));
        broker.addConnector(ActiveMQConst.brokerURL);
        broker.start();

        //System.out.println(broker.getBrokerName());
    }
}
