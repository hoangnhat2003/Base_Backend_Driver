package backend.drivor.base.consumer.receiver;

import backend.drivor.base.consumer.event.InitIndexEvent;
import backend.drivor.base.domain.components.RabbitMQInitial;
import backend.drivor.base.domain.document.BookingHistory;
import backend.drivor.base.domain.utils.GsonSingleton;
import backend.drivor.base.domain.utils.LoggerUtil;
import backend.drivor.base.service.searchbooking.service.BookingIndexManager;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

@RabbitListener(bindings = @QueueBinding(
        value = @Queue(value = RabbitMQInitial.QUEUE_BOOKING + "_INIT_INDEX"),
        exchange = @Exchange(value = RabbitMQInitial.EXCHANGE_BOOKING, type = ExchangeTypes.DIRECT),
        key = RabbitMQInitial.ROUTING_KEY_BOOKING + "_INIT_INDEX"
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
        } catch (Exception e) {
            LoggerUtil.exception(TAG, e);
        }

    }
}
