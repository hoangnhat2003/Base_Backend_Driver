package backend.drivor.base.config.rabbitmq;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import org.springframework.amqp.rabbit.core.ChannelCallback;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class DirectExchangeChannel {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    private String exchangeName;
    private Channel channel;
    private Connection connection;

    public DirectExchangeChannel(Connection connection, String exchangeName) throws IOException {
        this.exchangeName = exchangeName;
        this.connection = connection;
        this.channel = connection.createChannel();
    }

    public boolean ensureExchangeExists(String exchangeName) throws IOException {
        AMQP.Exchange.DeclareOk exchangeFromRabbit = channel.exchangeDeclarePassive(exchangeName);
        if (exchangeFromRabbit == null) {
            return false;
        }

        return true;
    }


    public void declareExchange() throws IOException {
        // exchangeDeclare( exchange, builtinExchangeType, durable)
        channel.exchangeDeclare(exchangeName, BuiltinExchangeType.DIRECT, true);
    }

    public void declareQueues(String queueName) throws IOException {
        // queueDeclare  - (queueName, durable, exclusive, autoDelete, arguments)
        channel.queueDeclare(queueName, true, false, false, null);
    }

    public void performQueueBinding(String queueName, String routingKey) throws IOException {
        // Create bindings - (queue, exchange, routingKey)
        channel.queueBind(queueName, exchangeName, routingKey);
    }

    public void subscribeMessage(String queueName) throws IOException {
        // basicConsume - ( queue, autoAck, deliverCallback, cancelCallback)
        channel.basicConsume(queueName, true, ((consumerTag, message) -> {
            System.out.println("[Received] [" + queueName + "]: " + consumerTag);
            System.out.println("[Received] [" + queueName + "]: " + new String(message.getBody()));
        }), consumerTag -> {
            System.out.println(consumerTag);
        });
    }

    public void publishMessage(String message, String routingKey) throws IOException {
        // basicPublish - ( exchange, routingKey, basicProperties, body)
        System.out.println("[Send] [" + routingKey + "]: " + message);
        channel.basicPublish(exchangeName, routingKey, null, message.getBytes());
    }
}
