package com.cn.pcwl.mqUtils;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.Connection;
import javax.jms.JMSException;

public class ActiveMqConnectionUtils {
	public static Connection newConnection(String url) throws JMSException{
		 ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(url);
	        // 2.创建连接
	        Connection connection = factory.createConnection();
		return connection;
		
	}
}