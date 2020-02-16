package com.cn.pcwl.mqUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.cn.pcwl.entity.ConnectEntity;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class RabbitMQConnectionUtils {
	public static Connection newConnection(ConnectEntity connect) throws IOException, TimeoutException {
		// 1.定义连接工厂
		ConnectionFactory factory = new ConnectionFactory();
		// 2.设置服务器地址
		factory.setHost(connect.getUrl());
		// 3.设置协议端口号
		factory.setPort(connect.getPost());
		// 4.设置vhost
		factory.setVirtualHost(connect.getHost());
		// 5.设置用户名称
		factory.setUsername(connect.getUserName());
		// 6.设置用户密码
		factory.setPassword(connect.getPassword());
		// 7.创建新的连接
		Connection newConnection = factory.newConnection();
		return newConnection;
	}
}
