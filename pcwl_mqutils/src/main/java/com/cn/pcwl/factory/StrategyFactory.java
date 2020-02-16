package com.cn.pcwl.factory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.util.StringUtils;

import com.cn.pcwl.strategy.MqStrategy;

/**
 *  �����ǳ�ʼ�� ��ͬ�Ĳ�����Ϊ  
 * @author Administrator
 *
 */
public class StrategyFactory {
	// ������ springά�� ��ʹ��һ��map ����  ���洢���������ɵĶ���
	private static Map<String, MqStrategy> strategyBean = new ConcurrentHashMap<String, MqStrategy>();
	
	
	/**
	 * 
	 * @param mqType ���Ϊ activeMQ/RabbitMQ �����Զ�ȥ��������
	 * @return
	 */
	private String classAdress;
	public MqStrategy getPayStrategy(String mqType){
	
		if("ActiveMQ".equals(mqType)){
			classAdress = "com.cn.pcwl.strategy.impl.ActiveMqStrategy";
		}else if("RabbitMQ".equals(mqType)){
			classAdress = "com.cn.pcwl.strategy.impl.RabbitMqStrategy";
		}else{
			try {
				throw new  Exception("�봫����ʹ��mq����  ActiveMQ ���� RabbitMQ");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		try {
			//1.�ж��� ��֮ǰ��û�г�ʼ�������bean �еĻ� ֱ�ӷ��� (���� ��ֹ�ظ�����) 
			MqStrategy beanStrategy = strategyBean.get(classAdress);
			if( beanStrategy != null){
				return beanStrategy;
			}
			
			
			Class<?> classbean = Class.forName(classAdress);
			MqStrategy  mqStrategy = (MqStrategy) classbean.newInstance();
			strategyBean.put(classAdress, mqStrategy);
			return mqStrategy;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		
		return  null;
		
		
		
	}
	
	
}

