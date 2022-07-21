package backend.drivor.base.consumer.receiver;

import backend.drivor.base.config.rabbitmq.RabbitMQConfig;
import backend.drivor.base.consumer.event.InitIndexEvent;
import backend.drivor.base.domain.document.BookingHistory;
import backend.drivor.base.domain.utils.GsonSingleton;
import backend.drivor.base.domain.utils.LoggerUtil;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(bindings = @QueueBinding(
        value = @Queue(value = RabbitMQConfig.QUEUE_BOOKING + "_INIT_INDEX", durable = "true"),
        exchange = @Exchange(value = RabbitMQConfig.EXCHANGE_BOOKING, durable = "true", type = ExchangeTypes.DIRECT)
))
public class InitIndexReceiver {

    private static final String TAG = InitIndexReceiver.class.getSimpleName();

    @Autowired
    private InitIndexEvent event;

    @RabbitHandler
    public void createBookingIndex(BookingHistory bookingHistory) {

        try {
            LoggerUtil.i(TAG, "Booking from queue : " + GsonSingleton.getInstance().toJson(bookingHistory));
            event.createBookingIndex(bookingHistory);
            LoggerUtil.i(TAG, String.format("Process data from queue {}", bookingHistory));
        } catch (Exception e) {
            LoggerUtil.exception(TAG, e);
        }

    }
}
