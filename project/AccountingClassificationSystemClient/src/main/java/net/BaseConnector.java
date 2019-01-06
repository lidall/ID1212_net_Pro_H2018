/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net;

/**
 *
 * @author Lida
 */
import startup.SystemClient;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class BaseConnector {
    protected Channel channel;
    protected Connection connection;
    protected String queueName;
    public BaseConnector(String queueName) throws Exception{
        this.queueName = queueName;
        ConnectionFactory factory = new ConnectionFactory();
        
        factory.setHost(SystemClient.SERVER_IP);

        factory.setUsername("lida");
        factory.setPassword("abc");
        factory.setVirtualHost("/");
        factory.setPort(5672);
        
        connection = factory.newConnection();
        
        channel = connection.createChannel();
  
        channel.queueDeclare(queueName, false, false, false, null);
    }
}
