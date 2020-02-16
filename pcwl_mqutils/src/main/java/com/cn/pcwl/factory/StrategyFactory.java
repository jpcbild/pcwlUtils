package com.cn.pcwl.factory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.util.StringUtils;

import com.cn.pcwl.strategy.MqStrategy;

/**
 *  帮我们初始化 不同的策略行为  
 * @author Administrator
 *
 */
public class StrategyFactory {
	// 不交给 spring维护 ，使用一个map 集合  来存储工厂类生成的对象，
	private static Map<String, MqStrategy> strategyBean = new ConcurrentHashMap<String, MqStrategy>();
	
	
	/**
	 * 
	 * @param mqType 入参为 activeMQ/RabbitMQ 他会自动去生成子类
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
				throw new  Exception("请传入你使用mq类型  ActiveMQ 或者 RabbitMQ");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		try {
			//1.判断下 ，之前有没有初始化过这个bean 有的话 直接返回 (单例 防止重复生成) 
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

