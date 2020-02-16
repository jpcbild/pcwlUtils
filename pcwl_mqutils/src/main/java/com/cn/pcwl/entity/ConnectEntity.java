package com.cn.pcwl.entity;

import com.rabbitmq.client.ConnectionFactory;

/**
 * 用于传入连接参数
 * @author jpc 
 * 
 */
public class ConnectEntity {
	
	private String url;
	private int post;
	private String host;  // 这个就是 连接的  rabbitmq 是属于哪个 库的
	private String userName;
	private String password;
	
	
	
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public int getPost() {
		return post;
	}
	public void setPost(int post) {
		this.post = post;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Override
	public String toString() {
		return "ConnectEntity [url=" + url + ", post=" + post + ", host=" + host + ", userName=" + userName
				+ ", password=" + password + "]";
	}
	
	
}

/*
 *
 * 
 * ConnectionFactory factory = new ConnectionFactory();
		// 2.设置服务器地址
		factory.setHost("127.0.0.1");
		// 3.设置协议端口号
		factory.setPort(5672);
		// 4.设置vhost
		factory.setVirtualHost("/test1");
		// 5.设置用户名称
		factory.setUsername("root");
		// 6.设置用户密码
		factory.setPassword("root");
 */

