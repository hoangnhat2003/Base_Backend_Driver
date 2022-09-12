package backend.drivor.base.consumer.receiver;

import backend.drivor.base.config.rabbitmq.RabbitMQConfig;
import backend.drivor.base.consumer.event.IndexChangeEvent;
import backend.drivor.base.domain.document.BookingHistory;
import backend.drivor.base.domain.utils.GsonSingleton;
import backend.drivor.base.domain.utils.LoggerUtil;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(bindings = @QueueBinding(
        value = @Queue(value = RabbitMQConfig.QUEUE_BOOKING + "_DELETE_INDEX", durable = "true"),
        exchange = @Exchange(value = RabbitMQConfig.EXCHANGE_BOOKING, durable = "true", type = ExchangeTypes.DIRECT)
))
public class DeleteIndexReceiver {

    private static final String TAG = DeleteIndexReceiver.class.getSimpleName();

    @Autowired
    private IndexChangeEvent event;

    @RabbitHandler
    public void deleteBookingIndex(BookingHistory dataFromQueue) {

        try {
            LoggerUtil.i(TAG, String.format("Booking from queue: {} " + dataFromQueue));
            event.deleteBookingIndex(dataFromQueue);
            LoggerUtil.i(TAG, String.format("Process data from queue: {}", dataFromQueue));
        } catch (Exception e) {
            LoggerUtil.exception(TAG, e);
        }

    }
}
