package com.cn.pcwl.test;

import com.cn.pcwl.entity.ConnectEntity;
import com.cn.pcwl.entity.MqBaseParamEntity;
import com.cn.pcwl.factory.StrategyFactory;
import com.cn.pcwl.strategy.MqStrategy;

/**
 * activemq消费者 测试类
 * @author jpc
 *
 */
public class ActivemqTestCus {
	public static void main(String[] args) throws Exception {
		ConnectEntity a = new ConnectEntity();
		MqBaseParamEntity b = new MqBaseParamEntity();
		
		a.setUrl("tcp://127.0.0.1:61617");
		b.setMqType("topic");
		b.setQueueName("jpcjzsgtc");
		b.setMessage("jpctc123455");
		StrategyFactory sb = new StrategyFactory();
	
		MqStrategy payStrategy = sb.getPayStrategy("ActiveMQ");
		payStrategy.sendMsg(a, b);
	}
}
