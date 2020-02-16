package com.cn.pcwl.strategy.impl;

import java.io.IOException;

import com.cn.pcwl.entity.ConnectEntity;
import com.cn.pcwl.entity.MqBaseParamEntity;
import com.cn.pcwl.mqUtils.RabbitMQConnectionUtils;
import com.cn.pcwl.strategy.MqStrategy;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

public class RabbitMqStrategy implements MqStrategy{

	@Override
	public void sendMsg(ConnectEntity connect, MqBaseParamEntity mqBaseParam) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("RabbitMq 发送。。。。。。。。。");
		
		checkParam(connect, mqBaseParam,"pro");
		
		
		
		Connection connection = RabbitMQConnectionUtils.newConnection(connect);
		// 2.创建通道
		Channel channel = connection.createChannel();
		// 3.绑定的交换机 参数1交互机名称 参数2 exchange类型
		channel.exchangeDeclare(mqBaseParam.getExchange_name(), mqBaseParam.getMqType());  // direct  fanout topic
		// 4.发送消息
		channel.basicPublish(mqBaseParam.getExchange_name(), mqBaseParam.getRoutingKey(), null, mqBaseParam.getMessage().getBytes());
		 System.out.println("生产者发送msg：" + mqBaseParam.getMessage());
		 // 5.关闭通道、连接
		 channel.close();
		 connection.close();
		// 注意：如果消费没有绑定交换机和队列，则消息会丢失
		 

	}

	@Override
	public void acceptMsg(ConnectEntity connect, MqBaseParamEntity mqBaseParam) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("RabbitMq 接受。。。。。。。。。");

		checkParam(connect, mqBaseParam,"cus");
		
		// 1.创建新的连接
		Connection connection = RabbitMQConnectionUtils.newConnection(connect);
		// 2.创建通道
		Channel channel = connection.createChannel();
		// 3.消费者关联队列
		channel.queueDeclare(mqBaseParam.getQueueName(), false, false, false, null);
		String routingKey = mqBaseParam.getRoutingKey();
		
		// 4.消费者绑定交换机 参数1 队列 参数2交换机 参数3 routingKey  这里先不考虑 多个routingkey 先把功能实现出来好了
////		if(routingkeyArr !=null || routingkeyArr.size()>0){
//			for (String routingkey : routingkeyArr) {
//				channel.queueBind(mqBaseParam.getQueueName(), mqBaseParam.getExchange_name(), routingkey);
//			}
//		}else {
//			channel.queueBind(mqBaseParam.getQueueName(), mqBaseParam.getExchange_name(), "");
//		}
		
		channel.queueBind(mqBaseParam.getQueueName(), mqBaseParam.getExchange_name(), routingKey);

		
		
		
		DefaultConsumer consumer = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope,
					com.rabbitmq.client.AMQP.BasicProperties properties, byte[] body) throws IOException {
				String msg = new String(body, "UTF-8");
				System.out.println("消费者获取生产者消息:" + msg);
			}
		};
		// 5.消费者监听队列消息
		channel.basicConsume(mqBaseParam.getQueueName(), true, consumer);
		
		
	}
	
	
	
	
	
	
	
	
	
	public void checkParam(ConnectEntity connect, MqBaseParamEntity mqBaseParam,String type) throws Exception{
		if(connect == null ){
			throw new Exception("connect不能为空 ");
		}
		if(mqBaseParam == null){
			throw new Exception("mqBaseParam不能为空 ");
		}
	
		
		String url = connect.getUrl();
		String mqType = mqBaseParam.getMqType();
		String queue = mqBaseParam.getQueueName();
		
		String host = connect.getHost();
		String password = connect.getPassword();
		int post = connect.getPost();
		String userName = connect.getUserName();
		
		String exchange_name = mqBaseParam.getExchange_name();
		String queueName = mqBaseParam.getQueueName();
		if("cus".equals(type)){
			if(queueName == null ){
				throw new Exception("mqBaseParam中的queueName不能为空");
			}
		}
		
		if(exchange_name == null ){
			throw new Exception("mqBaseParam中的exchange_name不能为空");
		}
		
		if(url == null){
			throw new Exception("connect 中的url不能为空");
		}
		if(host == null){
			throw new Exception("connect 中的host不能为空");

		}
		if(post == 0){
			throw new Exception("connect 中的post不能为0");
		}
		
		if(userName == null){
			throw new Exception("connect 中的userName不能为null");
		}
		
		if(mqType== null){

			throw new Exception("mqBaseParam 中的mqType不能为空");
			
		}
		if(queue == null){
			throw new Exception("mqBaseParam 中的queueName不能为空");
		}
		
	}
	
	
	
	
	
	
	
	

}
