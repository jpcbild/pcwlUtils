package com.cn.pcwl.strategy.impl;

import com.cn.pcwl.entity.ConnectEntity;
import com.cn.pcwl.entity.MqBaseParamEntity;
import com.cn.pcwl.mqUtils.RabbitMQConnectionUtils;
import com.cn.pcwl.strategy.MqStrategy;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import java.io.IOException;

/**
 * ClassName: RabbitMqStrategyTest
 * Description: 用来自己学习的类
 *
 * @author Crossover Jiang
 * @date 2020/3/31 0031 - 下午 6:10
 */
public class RabbitMqStrategyTest  implements MqStrategy {



        /**
         * 写项目的结论：
         * 	结论1：
         *
         * 		channel.exchangeDeclare
         * 		channel.queueDeclare(mqBaseParam.getQueueName(),true, false, false,null);
         * 	这两个声明队列 和声明交换机   如果中间件里没有 就会帮你生成   ，如果存在就不管， 如果你知道中间件中已经存在了 这个队列  不写也没事，
         *
         * 最重要的是
         *         channel.queueBind(mqBaseParam.getQueueName(),mqBaseParam.getExchange_name(),"");
         *        这个
         *
         *       他是绑定 交换机和队列 到你的 channel 里面  这样你才能接收到信息
         *
         *
         *  结论2：
         *
         *    绑定同个队列的消费者    类似于消费者集群。 他是用来实现的。
         *
         */

        @Override
        public void sendMsg(ConnectEntity connect, MqBaseParamEntity mqBaseParam) throws Exception {
            // TODO Auto-generated method stub
            System.out.println("RabbitMq 发送。。。。。。。。。");

            checkParam(connect, mqBaseParam,"pro");

            Connection connection = RabbitMQConnectionUtils.newConnection(connect);
            // 2.创建通道
            Channel channel = connection.createChannel();

            // 3.生产者绑定的交换机 参数1交互机名称 参数2 exchange类型
            //声明exchange
            //exchange:exchange名称
            //type:exchange类型
            //durable:exchange是否持久化(不代表消息持久化)
            //autoDelete:已经没有消费者了,服务器是否可以删除该Exchange
            //exchangeDeclare(String exchange, String type, boolean durable, boolean autoDelete,Map<String, Object> arguments)
            channel.exchangeDeclare(mqBaseParam.getExchange_name(), mqBaseParam.getMqType(),true,false,null);  // direct  fanout topic

            // 3.生产者绑定队列
            //声明queue
            //queue:queue名称
            //durable:queue是否持久化
            //exclusive:是否为当前连接的专用队列，在连接断开后，会自动删除该队列
            //autodelete：当没有任何消费者使用时，自动删除该队列
            //queueDeclare(String queue, boolean durable, boolean exclusive, boolean autoDelete,Map<String, Object> arguments)
            channel.queueDeclare(mqBaseParam.getQueueName(),true, false, false,null);

            //4.生产者  交换机绑定队列
            //queue:queue名称
            //exchange:exchange名称
            //routingKey:路由键;用来绑定queue和exchange
            //queueBind(String queue, String exchange, String routingKey)
            channel.queueBind(mqBaseParam.getQueueName(),mqBaseParam.getExchange_name(),"");

//        channel.confirmSelect();

            // 4.发送消息
            channel.basicPublish(mqBaseParam.getExchange_name(), mqBaseParam.getRoutingKey(), null, mqBaseParam.getMessage().getBytes());
            System.out.println("生产者发送msg：" + mqBaseParam.getMessage());
            // 5.关闭通道、 连接
            channel.close();
            connection.close();
            // 注意：如果消费没有绑定交换机和队列，则消息会丢失


        }

        @Override
        public void acceptMsg(ConnectEntity connect, MqBaseParamEntity mqBaseParam) throws Exception {
            // TODO Auto-generated method stub
            System.out.println("RabbitMq 接受。。。。。。。。。");

            checkParam(connect, mqBaseParam,"cus");

            // 1.创建新的连接
            Connection connection = RabbitMQConnectionUtils.newConnection(connect);
            // 2.创建通道
            Channel channel = connection.createChannel();
            // 3.消费者关联队列
            channel.queueDeclare(mqBaseParam.getQueueName(),true, false, false,null);
            channel.exchangeDeclare(mqBaseParam.getExchange_name(), mqBaseParam.getMqType(),true,false,null);  // direct  fanout topic

            String routingKey = mqBaseParam.getRoutingKey();

            // 4.消费者绑定交换机 参数1 队列 参数2交换机 参数3 routingKey  这里先不考虑 多个routingkey 先把功能实现出来好了
////		if(routingkeyArr !=null || routingkeyArr.size()>0){
//			for (String routingkey : routingkeyArr) {
//				channel.queueBind(mqBaseParam.getQueueName(), mqBaseParam.getExchange_name(), routingkey);
//			}
//		}else {
//			channel.queueBind(mqBaseParam.getQueueName(), mqBaseParam.getExchange_name(), "");
//		}

            channel.queueBind(mqBaseParam.getQueueName(), mqBaseParam.getExchange_name(), routingKey);
            DefaultConsumer consumer = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope,
                                           com.rabbitmq.client.AMQP.BasicProperties properties, byte[] body) throws IOException {
                    String msg = new String(body, "UTF-8");
                    System.out.println("消费者获取生产者消息:" + msg);
                }
            };
            // 5.消费者监听队列消息
            channel.basicConsume(mqBaseParam.getQueueName(), true, consumer);


        }









        public void checkParam(ConnectEntity connect, MqBaseParamEntity mqBaseParam,String type) throws Exception{
            if(connect == null ){
                throw new Exception("connect不能为空 ");
            }
            if(mqBaseParam == null){
                throw new Exception("mqBaseParam不能为空 ");
            }


            String url = connect.getUrl();
            String mqType = mqBaseParam.getMqType();
            String queue = mqBaseParam.getQueueName();

            String host = connect.getHost();
            String password = connect.getPassword();
            int post = connect.getPost();
            String userName = connect.getUserName();

            String exchange_name = mqBaseParam.getExchange_name();
            String queueName = mqBaseParam.getQueueName();
            if("cus".equals(type)){
                if(queueName == null ){
                    throw new Exception("mqBaseParam中的queueName不能为空");
                }
            }

            if(exchange_name == null ){
                throw new Exception("mqBaseParam中的exchange_name不能为空");
            }

            if(url == null){
                throw new Exception("connect 中的url不能为空");
            }
            if(host == null){
                throw new Exception("connect 中的host不能为空");

            }
            if(post == 0){
                throw new Exception("connect 中的post不能为0");
            }

            if(userName == null){
                throw new Exception("connect 中的userName不能为null");
            }

            if(mqType== null){

                throw new Exception("mqBaseParam 中的mqType不能为空");

            }
            if(queue == null){
                throw new Exception("mqBaseParam 中的queueName不能为空");
            }

        }





    }
