package com.cn.pcwl.test;

import com.cn.pcwl.entity.ConnectEntity;
import com.cn.pcwl.entity.MqBaseParamEntity;
import com.cn.pcwl.factory.StrategyFactory;
import com.cn.pcwl.strategy.MqStrategy;
/**
 * activemq生产者 测试类
 * @author jpc
 *
 */
public class ActivemqTestPro {
	public static void main(String[] args) throws Exception {

		ConnectEntity a = new ConnectEntity();
		MqBaseParamEntity b = new MqBaseParamEntity();
		
		a.setUrl("tcp://127.0.0.1:61617");
		b.setMqType("topic");
		b.setQueueName("jpcjzsgtc");
//		ActiveMqStrategy sb = new ActiveMqStrategy();
//		sb.checkParam(a,b);
//		
		StrategyFactory sb = new StrategyFactory();
	
		MqStrategy payStrategy = sb.getPayStrategy("ActiveMQ");  //通过传入 activemq 或者 rabbitmq 获取到指定的mq模式 并且发送信息
//		payStrategy.sendMsg(a, b);
		payStrategy.acceptMsg(a, b);
			
	}
}
