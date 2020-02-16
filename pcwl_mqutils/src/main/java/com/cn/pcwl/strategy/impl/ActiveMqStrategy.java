package com.cn.pcwl.strategy.impl;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.springframework.util.StringUtils;

import com.cn.pcwl.entity.ConnectEntity;
import com.cn.pcwl.entity.MqBaseParamEntity;
import com.cn.pcwl.mqUtils.ActiveMqConnectionUtils;
import com.cn.pcwl.strategy.MqStrategy;

/**
 * 
 * @author Administrator
 *
 */
public class ActiveMqStrategy implements MqStrategy {

	@Override
	public void sendMsg(ConnectEntity connect, MqBaseParamEntity mqBaseParam) throws Exception {
		System.out.println("AcitveMq 发送。。。。。。。。。");
		//0.判断必要参数是否存在
		checkParam(connect,mqBaseParam);
		
		
			
		//1.获取连接
		Connection connection = ActiveMqConnectionUtils.newConnection(connect.getUrl());
		 
        // 3.创建会话 参数1 设置是否需要以事务方式提交 参数2 消息方式 采用自动签收
        connection.start();// 启动连接
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        // 4.创建目标(队列)
        if("topic".equals(mqBaseParam.getMqType())){
        	
        	Topic topic = session.createTopic(mqBaseParam.getQueueName());
        	topicSend(connection, session, topic,mqBaseParam.getMessage());
        	return;
        }
        if("queue".equals(mqBaseParam.getMqType())){
        	
        	Queue queue = session.createQueue(mqBaseParam.getQueueName()); // 就是这个 来判断是topic 或者 点对点
        	queueSend(connection, session, queue,mqBaseParam.getMessage());
        	return;
        }
		
		
		
	}

	@Override
	public void acceptMsg(ConnectEntity connect, MqBaseParamEntity mqBaseParam) throws Exception {
		//1.校验下参数  
		checkParam(connect,mqBaseParam);

		
		
		
		System.out.println("ActiveMq 接受。。。。。。。。。");
		Connection cnnection = ActiveMqConnectionUtils.newConnection(connect.getUrl());
		cnnection.start();
		// 4.创建Session 不开启事务,自动签收模式
		Session session = cnnection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		// 5.创建一个目标
		if("topic".equals(mqBaseParam.getMqType())){
			topicCus(session, mqBaseParam.getQueueName());
			return;
		}else if("queue".equals(mqBaseParam.getMqType())){
			queueCus(session, mqBaseParam.getQueueName());
			return;
		}
	
	}

	
	
	public void checkParam(ConnectEntity connect, MqBaseParamEntity mqBaseParam) throws Exception{
		if(connect == null ){
			throw new Exception("connect不能为空 ");
		}
		if(mqBaseParam == null){
			throw new Exception("mqBaseParam不能为空 ");
		}
	
		String url = connect.getUrl();
		String mqType = mqBaseParam.getMqType();
		String queue = mqBaseParam.getQueueName();
		if(url == null){
			throw new Exception("connect 中的url不能为空");
		}
		if((!"topic".equals(mqType))&&(!"queue".equals(mqType))){
			throw new Exception("mqBaseParam 的type 类型有问题， 请传入 queue 或者 topic ");
		}
		if(mqType== null){

			throw new Exception("connect 中的url不能为空");
			
		}
		if(queue == null){
			throw new Exception("mqBaseParam 中的queueName不能为空");
		}
		
	}
	
	
	
	
	
	
	//发布订阅 发送消息
	private void topicSend(Connection connection,Session session, Topic topic,String msg) throws JMSException{
		   MessageProducer producer = session.createProducer(topic);

	            // 6.创建 消息
	            TextMessage textMessage = session.createTextMessage(msg);
	            // 7.发送消息
	            producer.send(textMessage);

	        
	        // 8.关闭连接
	        connection.close();
	        System.out.println("消息发送完毕!");
	}
	
	//点对点发送消息
	private void queueSend(Connection connection,Session session, Queue queue,String msg) throws JMSException{
		   MessageProducer producer = session.createProducer(queue);

		   // 6.创建 消息
        TextMessage textMessage = session.createTextMessage(msg);
        // 7.发送消息
	            producer.send(textMessage);

	        // 8.关闭连接
	        connection.close();
	        System.out.println("消息发送完毕!");
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//点对点接受消息
	private void queueCus(Session session,String queueName) throws JMSException {
		Queue queue = session.createQueue(queueName);
		// 6.创建生产者
		MessageConsumer createConsumer = session.createConsumer(queue);
		createConsumer.setMessageListener(new MessageListener() {
	
			public void onMessage(Message message) {
				try {
					TextMessage textMessage = (TextMessage) message;
					System.out.println("消费者消费消息:" + textMessage.getText());
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		});
	}
	//点对点发送消息
	private void topicCus(Session session,String topicName) throws JMSException {
		
	        // 4.创建目标(主题)
	        Topic topic = session.createTopic(topicName);
	        // 5.创建消费者
	        MessageConsumer consumer = session.createConsumer(topic);
	        // 6.启动监听 监听消息
	        consumer.setMessageListener(new MessageListener() {

	            public void onMessage(Message message) {
	                try {
	                    TextMessage textMessage = (TextMessage) message;
	                    System.out.println("消费者消息生产者内容:" + textMessage.getText());
	                } catch (Exception e) {
	                    // TODO: handle exception
	                }
	            }
	        });
	}// 不要关闭连接
	
}
