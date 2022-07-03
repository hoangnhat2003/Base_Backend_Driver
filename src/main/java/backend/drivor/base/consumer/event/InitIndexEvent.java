package backend.drivor.base.consumer.event;

import backend.drivor.base.domain.document.BookingHistory;

public interface InitIndexEvent {
    void createBookingIndex(BookingHistory bookingHistory);

}
