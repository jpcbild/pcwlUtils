package com.cn.pcwl.entity;
/**
 * 用于传入 mq 参数
 * @author jpc
 *
 */
public  class MqBaseParamEntity {
    private String queueName;
    private String mqType;
    private String message;
    private String exchange_name;
 	private String routingKey;
 
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	public String getExchange_name() {
 		return exchange_name;
 	}
 	public void setExchange_name(String exchange_name) {
 		this.exchange_name = exchange_name;
 	}
 	public String getRoutingKey() {
 		return routingKey;
 	}
 	public void setRoutingKey(String routingKey) {
 		this.routingKey = routingKey;
 	}
 	
     public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getQueueName() {
		return queueName;
	}
	public void setQueueName(String queueName) {
		this.queueName = queueName;
	}
	public String getMqType() {
		return mqType;
	}
	public void setMqType(String mqType) {
		this.mqType = mqType;
	}
     
     
     
	
	
}
