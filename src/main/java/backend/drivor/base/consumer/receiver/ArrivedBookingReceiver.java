package backend.drivor.base.consumer.receiver;

import backend.drivor.base.config.rabbitmq.RabbitMQConfig;
import backend.drivor.base.consumer.event.BookingEvent;
import backend.drivor.base.domain.document.BookingHistory;
import backend.drivor.base.domain.utils.LoggerUtil;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(bindings = @QueueBinding(
        value = @Queue(value = RabbitMQConfig.QUEUE_BOOKING + "_ARRIVED_BOOKING", durable = "true"),
        exchange = @Exchange(value = RabbitMQConfig.EXCHANGE_BOOKING, durable = "true", type = ExchangeTypes.DIRECT)
))
public class ArrivedBookingReceiver {

    private static final String TAG = ArrivedBookingReceiver.class.getSimpleName();

    @Autowired
    private BookingEvent event;

    @RabbitHandler
    public void arrivedBookingRequest(BookingHistory dataFromQueue) {

        try {
            LoggerUtil.i(TAG, String.format("Booking from queue: {} " + dataFromQueue));
            event.arrivedBookingRequest(dataFromQueue);
            LoggerUtil.i(TAG, String.format("Process data from queue: {}", dataFromQueue));
        } catch (Exception e) {
            LoggerUtil.exception(TAG, e);
        }
    }
}
