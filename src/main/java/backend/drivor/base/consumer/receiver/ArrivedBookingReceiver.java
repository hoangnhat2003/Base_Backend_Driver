package backend.drivor.base.consumer.receiver;

import backend.drivor.base.config.rabbitmq.RabbitMQConfig;
import backend.drivor.base.consumer.event.BookingEvent;
import backend.drivor.base.domain.document.BookingHistory;
import backend.drivor.base.domain.utils.GsonSingleton;
import backend.drivor.base.domain.utils.LoggerUtil;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = RabbitMQConfig.QUEUE_BOOKING + "_ARRIVED_BOOKING")
public class ArrivedBookingReceiver {

    private static final String TAG = ArrivedBookingReceiver.class.getSimpleName();

    @Autowired
    private BookingEvent event;

    @RabbitHandler
    public void arrivedBookingRequest(String dataFromQueue) {

        try {
            LoggerUtil.i(TAG, "Booking from queue : " + dataFromQueue);
            BookingHistory bookingHistory = GsonSingleton.getInstance().fromJson(dataFromQueue, BookingHistory.class);
            event.arrivedBookingRequest(bookingHistory);
            LoggerUtil.i(TAG, String.format("Process data from queue {}", dataFromQueue));
        } catch (Exception e) {
            LoggerUtil.exception(TAG, e);
        }

    }

}
