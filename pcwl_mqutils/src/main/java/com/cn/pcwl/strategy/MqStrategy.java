package com.cn.pcwl.strategy;

import com.cn.pcwl.entity.ConnectEntity;
import com.cn.pcwl.entity.MqBaseParamEntity;

public interface MqStrategy {
	//�����߷�����Ϣ
	public void sendMsg(ConnectEntity connect, MqBaseParamEntity mqBaseParam) throws Exception;
	
	//�����߽�����Ϣ
	public void acceptMsg(ConnectEntity connect, MqBaseParamEntity mqBaseParam) throws Exception;
	
}
