package backend.drivor.base.domain.components;

import backend.drivor.base.config.rabbitmq.Producer;
import backend.drivor.base.domain.model.MqMessage;
import backend.drivor.base.domain.utils.GsonSingleton;
import backend.drivor.base.domain.utils.LoggerUtil;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Component
public class RabbitMQSender {


    private static final String TAG = RabbitMQSender.class.getSimpleName();

    @Autowired
    private Producer producer;

    @Autowired
    private AmqpTemplate rabbitTemplate;

    public void send(MqMessage message) throws IOException, TimeoutException {

        producer.start(message.getExchange(), message.getQueue(), message.getRoutingKey());

        rabbitTemplate.convertAndSend(message.getExchange(), message.getRoutingKey(), message.getMessage());
        LoggerUtil.i(TAG ,"Sending Message to the Queue : " + GsonSingleton.getInstance().toJson(message.getMessage()));
    }

}
