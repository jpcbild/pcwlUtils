package com.cn.pcwl.strategy;

import com.cn.pcwl.entity.ConnectEntity;
import com.cn.pcwl.entity.MqBaseParamEntity;

public interface MqStrategy {
	//生产者发送信息
	public void sendMsg(ConnectEntity connect, MqBaseParamEntity mqBaseParam) throws Exception;
	
	//消费者接受信息
	public void acceptMsg(ConnectEntity connect, MqBaseParamEntity mqBaseParam) throws Exception;
	
}
