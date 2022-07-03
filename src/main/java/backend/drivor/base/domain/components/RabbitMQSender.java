package backend.drivor.base.domain.components;

import backend.drivor.base.domain.model.MqMessage;
import backend.drivor.base.domain.utils.GsonSingleton;
import backend.drivor.base.domain.utils.LoggerUtil;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQSender {

    private static final String TAG = RabbitMQSender.class.getSimpleName();

    @Autowired
    private AmqpTemplate rabbitTemplate;

    public void send(MqMessage message) {
        rabbitTemplate.convertAndSend(message.getExchange(), message.getRoutingKey(), message.getMessage());
        LoggerUtil.i(TAG ,"Sending Message to the Queue : " + GsonSingleton.getInstance().toJson(message.getMessage()));
    }


}
