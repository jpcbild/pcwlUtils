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
 * @author
 *
 */
public class ActiveMqStrategy implements MqStrategy {

	@Override
	public void sendMsg(ConnectEntity connect, MqBaseParamEntity mqBaseParam) throws Exception {
		System.out.println("AcitveMq ���͡�����������������");
		//0.�жϱ�Ҫ�����Ƿ����
		checkParam(connect,mqBaseParam);



		//1.��ȡ����
		Connection connection = ActiveMqConnectionUtils.newConnection(connect.getUrl());

		// 3.�����Ự ����1 �����Ƿ���Ҫ������ʽ�ύ ����2 ��Ϣ��ʽ �����Զ�ǩ��
		connection.start();// ��������
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		// 4.����Ŀ��(����)
		if("topic".equals(mqBaseParam.getMqType())){

			Topic topic = session.createTopic(mqBaseParam.getQueueName());
			topicSend(connection, session, topic,mqBaseParam.getMessage());
			return;
		}
		if("queue".equals(mqBaseParam.getMqType())){

			Queue queue = session.createQueue(mqBaseParam.getQueueName()); // ������� ���ж���topic ���� ��Ե�
			queueSend(connection, session, queue,mqBaseParam.getMessage());
			return;
		}



	}

	@Override
	public void acceptMsg(ConnectEntity connect, MqBaseParamEntity mqBaseParam) throws Exception {
		//1.У���²���  
		checkParam(connect,mqBaseParam);




		System.out.println("ActiveMq ���ܡ�����������������");
		Connection cnnection = ActiveMqConnectionUtils.newConnection(connect.getUrl());
		cnnection.start();
		// 4.����Session ����������,�Զ�ǩ��ģʽ
		Session session = cnnection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		// 5.����һ��Ŀ��
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
			throw new Exception("connect����Ϊ�� ");
		}
		if(mqBaseParam == null){
			throw new Exception("mqBaseParam����Ϊ�� ");
		}

		String url = connect.getUrl();
		String mqType = mqBaseParam.getMqType();
		String queue = mqBaseParam.getQueueName();
		if(url == null){
			throw new Exception("connect �е�url����Ϊ��");
		}
		if((!"topic".equals(mqType))&&(!"queue".equals(mqType))){
			throw new Exception("mqBaseParam ��type ���������⣬ �봫�� queue ���� topic ");
		}
		if(mqType== null){

			throw new Exception("connect �е�url����Ϊ��");

		}
		if(queue == null){
			throw new Exception("mqBaseParam �е�queueName����Ϊ��");
		}

	}






	//�������� ������Ϣ
	private void topicSend(Connection connection,Session session, Topic topic,String msg) throws JMSException{
		MessageProducer producer = session.createProducer(topic);

		// 6.���� ��Ϣ
		TextMessage textMessage = session.createTextMessage(msg);
		// 7.������Ϣ
		producer.send(textMessage);


		// 8.�ر�����
		connection.close();
		System.out.println("��Ϣ�������!");
	}

	//��Ե㷢����Ϣ
	private void queueSend(Connection connection,Session session, Queue queue,String msg) throws JMSException{
		MessageProducer producer = session.createProducer(queue);

		// 6.���� ��Ϣ
		TextMessage textMessage = session.createTextMessage(msg);
		// 7.������Ϣ
		producer.send(textMessage);

		// 8.�ر�����
		connection.close();
		System.out.println("��Ϣ�������!");
	}















	//��Ե������Ϣ
	private void queueCus(Session session,String queueName) throws JMSException {
		Queue queue = session.createQueue(queueName);
		// 6.����������
		MessageConsumer createConsumer = session.createConsumer(queue);
		createConsumer.setMessageListener(new MessageListener() {

			public void onMessage(Message message) {
				try {
					TextMessage textMessage = (TextMessage) message;
					System.out.println("������������Ϣ:" + textMessage.getText());
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		});
	}
	//��Ե㷢����Ϣ
	private void topicCus(Session session,String topicName) throws JMSException {

		// 4.����Ŀ��(����)
		Topic topic = session.createTopic(topicName);
		// 5.����������
		MessageConsumer consumer = session.createConsumer(topic);
		// 6.�������� ������Ϣ
		consumer.setMessageListener(new MessageListener() {

			public void onMessage(Message message) {
				try {
					TextMessage textMessage = (TextMessage) message;
					System.out.println("��������Ϣ����������:" + textMessage.getText());
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		});
	}// ��Ҫ�ر�����

}
