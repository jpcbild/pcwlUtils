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
		System.out.println("RabbitMq ���͡�����������������");
		
		checkParam(connect, mqBaseParam,"pro");
		
		
		
		Connection connection = RabbitMQConnectionUtils.newConnection(connect);
		// 2.����ͨ��
		Channel channel = connection.createChannel();
		// 3.�󶨵Ľ����� ����1���������� ����2 exchange����
		channel.exchangeDeclare(mqBaseParam.getExchange_name(), mqBaseParam.getMqType());  // direct  fanout topic
		// 4.������Ϣ
		channel.basicPublish(mqBaseParam.getExchange_name(), mqBaseParam.getRoutingKey(), null, mqBaseParam.getMessage().getBytes());
		 System.out.println("�����߷���msg��" + mqBaseParam.getMessage());
		 // 5.�ر�ͨ��������
		 channel.close();
		 connection.close();
		// ע�⣺�������û�а󶨽������Ͷ��У�����Ϣ�ᶪʧ
		 

	}

	@Override
	public void acceptMsg(ConnectEntity connect, MqBaseParamEntity mqBaseParam) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("RabbitMq ���ܡ�����������������");

		checkParam(connect, mqBaseParam,"cus");
		
		// 1.�����µ�����
		Connection connection = RabbitMQConnectionUtils.newConnection(connect);
		// 2.����ͨ��
		Channel channel = connection.createChannel();
		// 3.�����߹�������
		channel.queueDeclare(mqBaseParam.getQueueName(), false, false, false, null);
		String routingKey = mqBaseParam.getRoutingKey();
		
		// 4.�����߰󶨽����� ����1 ���� ����2������ ����3 routingKey  �����Ȳ����� ���routingkey �Ȱѹ���ʵ�ֳ�������
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
				System.out.println("�����߻�ȡ��������Ϣ:" + msg);
			}
		};
		// 5.�����߼���������Ϣ
		channel.basicConsume(mqBaseParam.getQueueName(), true, consumer);
		
		
	}
	
	
	
	
	
	
	
	
	
	public void checkParam(ConnectEntity connect, MqBaseParamEntity mqBaseParam,String type) throws Exception{
		if(connect == null ){
			throw new Exception("connect����Ϊ�� ");
		}
		if(mqBaseParam == null){
			throw new Exception("mqBaseParam����Ϊ�� ");
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
				throw new Exception("mqBaseParam�е�queueName����Ϊ��");
			}
		}
		
		if(exchange_name == null ){
			throw new Exception("mqBaseParam�е�exchange_name����Ϊ��");
		}
		
		if(url == null){
			throw new Exception("connect �е�url����Ϊ��");
		}
		if(host == null){
			throw new Exception("connect �е�host����Ϊ��");

		}
		if(post == 0){
			throw new Exception("connect �е�post����Ϊ0");
		}
		
		if(userName == null){
			throw new Exception("connect �е�userName����Ϊnull");
		}
		
		if(mqType== null){

			throw new Exception("mqBaseParam �е�mqType����Ϊ��");
			
		}
		if(queue == null){
			throw new Exception("mqBaseParam �е�queueName����Ϊ��");
		}
		
	}
	
	
	
	
	
	
	
	

}
