package com.cn.pcwl.test;

import com.cn.pcwl.entity.ConnectEntity;
import com.cn.pcwl.entity.MqBaseParamEntity;
import com.cn.pcwl.factory.StrategyFactory;
import com.cn.pcwl.strategy.MqStrategy;

public class RabbitmqTestCus {
	public static void main(String[] args) throws Exception {
		

		
		ConnectEntity a = new ConnectEntity();
		MqBaseParamEntity b = new MqBaseParamEntity();
		
		a.setHost("/test1");
		a.setPassword("root");
		a.setUrl("127.0.0.1");
		a.setUserName("root");
		a.setPost(5672);
		
		b.setExchange_name("wtopic");
		b.setMessage("test12345621312========================");
		b.setMqType("topic");
		b.setRoutingKey("jpctest1234");
		b.setQueueName("jpcwd123456");
		StrategyFactory factory = new StrategyFactory();
		MqStrategy mqStrategy = factory.getPayStrategy("RabbitMQ");
		
		mqStrategy.acceptMsg(a, b);
	}
}
