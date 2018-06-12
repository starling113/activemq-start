package org.lingg.activemq.demo1.broker;

import org.apache.activemq.broker.BrokerFactory;
import org.apache.activemq.broker.BrokerService;
import org.junit.Test;

import java.net.URI;

public class BrokerTest {

    @Test
    public void test() throws Exception {
        BrokerService broker = new BrokerService();
        // 启用broker的JMX监控功能
        broker.setUseJmx(true);
        // 设置broker名字
        broker.setBrokerName("MyBroker");
        // 是否使用持久化
        broker.setPersistent(false);
        // 添加连接协议，地址
        broker.addConnector("tcp://localhost:61616");
        broker.start();


    }

    @Test
    public void test2() throws Exception{
        String uri = "properties:broker.properties";
        BrokerService broker = BrokerFactory.createBroker(new URI(uri));
        broker.addConnector("tcp://192.168.121.1:61616");
        broker.start();
    }
}
