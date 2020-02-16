package com.cn.pcwl.mqUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.cn.pcwl.entity.ConnectEntity;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class RabbitMQConnectionUtils {
	public static Connection newConnection(ConnectEntity connect) throws IOException, TimeoutException {
		// 1.�������ӹ���
		ConnectionFactory factory = new ConnectionFactory();
		// 2.���÷�������ַ
		factory.setHost(connect.getUrl());
		// 3.����Э��˿ں�
		factory.setPort(connect.getPost());
		// 4.����vhost
		factory.setVirtualHost(connect.getHost());
		// 5.�����û�����
		factory.setUsername(connect.getUserName());
		// 6.�����û�����
		factory.setPassword(connect.getPassword());
		// 7.�����µ�����
		Connection newConnection = factory.newConnection();
		return newConnection;
	}
}
