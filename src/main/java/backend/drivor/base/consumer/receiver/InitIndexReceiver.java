package backend.drivor.base.consumer.receiver;

import backend.drivor.base.consumer.event.InitIndexEvent;
import backend.drivor.base.service.searchbooking.service.BookingIndexManager;
import org.springframework.beans.factory.annotation.Autowired;

public class InitIndexReceiver implements InitIndexEvent {

    @Autowired
    private BookingIndexManager bookingIndexManager;
}
