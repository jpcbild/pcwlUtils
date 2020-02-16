package com.cn.pcwl.mqUtils;
import javax.jms.Connection;
import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

public class ActiveMqConnectionUtils {
	public static Connection newConnection(String url) throws JMSException{
		 ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(url);
	        // 2.创建连接
	        Connection connection = factory.createConnection();
		return connection;
		
	}
}