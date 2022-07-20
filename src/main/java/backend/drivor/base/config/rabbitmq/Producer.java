package backend.drivor.base.config.rabbitmq;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Connection;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Component
public class Producer {

    private DirectExchangeChannel channel;

    public void start(String exchange, String queue, String routingKey) throws IOException, TimeoutException {

        // Create connection
        Connection connection = ConnectionManager.createConnection();

        // Create channel
        channel = new DirectExchangeChannel(connection, exchange);

        // Create direct exchange
        channel.declareExchange();

        // Create queues
        channel.declareQueues(queue);

        // Binding queues with routing keys
        channel.performQueueBinding(queue, routingKey);

    }
}
