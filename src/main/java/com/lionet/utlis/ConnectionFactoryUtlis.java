package com.lionet.utlis;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeoutException;

public class ConnectionFactoryUtlis {

    public static Connection getConnection() {


        // 创建链接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
       // connectionFactory.setSharedExecutor(executorService);
        // 设置地址
        connectionFactory.setHost("127.0.0.1");

        // 设置 AMQP 端口号

        connectionFactory.setPort(5672);

        // 设置 vhost (虚拟数据库)
        connectionFactory.setVirtualHost("/_lionet_vri_host");

        // 设置用户名
        connectionFactory.setUsername("guest");

        // 设置密码
        connectionFactory.setPassword("guest");

        // 为了节省浪费连接资源默认 10个线程
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        Connection connection = null;
        try {
            // 获取 RabbitMQ 连接
            connection = connectionFactory.newConnection(executorService);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        if (connection == null) {
            throw new RuntimeException("创建 RabbitMQ 连接失败");
        }

        return connection;
    }
}
