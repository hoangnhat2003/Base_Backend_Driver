package backend.drivor.base.consumer.event;

import backend.drivor.base.domain.document.BookingHistory;

public interface IndexChangeEvent {
    void createBookingIndex(BookingHistory bookingHistory);

    void deleteBookingIndex(Long id);

}
