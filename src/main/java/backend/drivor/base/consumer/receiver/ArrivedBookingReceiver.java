package backend.drivor.base.consumer.receiver;

import backend.drivor.base.consumer.event.ArrivedBookingEvent;
import backend.drivor.base.service.distribution.BookingDistribution;
import org.springframework.beans.factory.annotation.Autowired;

public class ArrivedBookingReceiver implements ArrivedBookingEvent {

    @Autowired
    private BookingDistribution bookingDistribution;
}
