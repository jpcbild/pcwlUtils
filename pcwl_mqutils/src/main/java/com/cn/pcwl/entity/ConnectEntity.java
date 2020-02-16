package com.cn.pcwl.entity;

import com.rabbitmq.client.ConnectionFactory;

/**
 * ���ڴ������Ӳ���
 * @author jpc 
 * 
 */
public class ConnectEntity {
	
	private String url;
	private int post;
	private String host;  // ������� ���ӵ�  rabbitmq �������ĸ� ���
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
		// 2.���÷�������ַ
		factory.setHost("127.0.0.1");
		// 3.����Э��˿ں�
		factory.setPort(5672);
		// 4.����vhost
		factory.setVirtualHost("/test1");
		// 5.�����û�����
		factory.setUsername("root");
		// 6.�����û�����
		factory.setPassword("root");
 */

