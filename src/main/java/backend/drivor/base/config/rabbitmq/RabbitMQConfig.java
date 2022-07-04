package backend.drivor.base.config.rabbitmq;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.ConditionalRejectingErrorHandler;
import org.springframework.amqp.rabbit.support.ListenerExecutionFailedException;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.util.ErrorHandler;

@EnableRabbit
@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE_BOOKING = "BOOKING";

    public static final String QUEUE_BOOKING = "BOOKING_QUEUE";

    public static final String ROUTING_KEY_BOOKING = "BOOKING_ROUTING";

    /**
     * Source: https://www.springcloud.io/post/2022-03/messaging-using-rabbitmq-in-spring-boot-application/#gsc.tab=0
     */
    @Value("${rabbitmq.username}")
    private String username;
    @Value("${rabbitmq.password}")
    private String password;
    @Value("${rabbitmq.host}")
    private String host;
    @Value("${rabbitmq.reply.timeout}")
    private Integer replyTimeout;
    @Value("${rabbitmq.concurrent.consumers}")
    private Integer concurrentConsumers;
    @Value("${rabbitmq.max.concurrent.consumers}")
    private Integer maxConcurrentConsumers;
    @Value("${rabbitmq.virtualhost}")
    private String virtualHost;


    @Bean
    public MessageConverter messageConverter() {
        return  new Jackson2JsonMessageConverter();
    }

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setVirtualHost(virtualHost);
        connectionFactory.setHost(host);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        return connectionFactory;
    }
    @Bean
    public AmqpTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter());
        rabbitTemplate.setReplyTimeout(replyTimeout);
        rabbitTemplate.setUseDirectReplyToContainer(false);
        return rabbitTemplate;
    }
    @Bean
    public AmqpAdmin amqpAdmin() {
        return new RabbitAdmin(connectionFactory());
    }
    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory() {
        final SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory());
        factory.setMessageConverter(messageConverter());
        factory.setConcurrentConsumers(concurrentConsumers);
        factory.setMaxConcurrentConsumers(maxConcurrentConsumers);
        factory.setErrorHandler(errorHandler());
        return factory;
    }

    @Bean
    public ErrorHandler errorHandler() {
        return new ConditionalRejectingErrorHandler(new MyFatalExceptionStrategy());
    }
    public static class MyFatalExceptionStrategy extends ConditionalRejectingErrorHandler.DefaultExceptionStrategy {
        private final Logger logger = LogManager.getLogger(getClass());
        @Override
        public boolean isFatal(Throwable t) {
            if (t instanceof ListenerExecutionFailedException) {
                ListenerExecutionFailedException lefe = (ListenerExecutionFailedException) t;
                logger.error("Failed to process inbound message from queue "
                        + lefe.getFailedMessage().getMessageProperties().getConsumerQueue()
                        + "; failed message: " + lefe.getFailedMessage(), t);
            }
            return super.isFatal(t);
        }
    }
}