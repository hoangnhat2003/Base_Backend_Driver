package backend.drivor.base.consumer.event;

import backend.drivor.base.domain.document.BookingHistory;
import backend.drivor.base.domain.response.GeneralSubmitResponse;

public interface BookingEvent {

    GeneralSubmitResponse arrivedBookingRequest(BookingHistory bookingHistory);

}
