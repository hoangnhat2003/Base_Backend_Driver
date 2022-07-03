package backend.drivor.base.service.searchbooking.service;

import backend.drivor.base.consumer.event.InitIndexEvent;
import backend.drivor.base.domain.document.BookingHistory;

public class BookingIndexManager implements InitIndexEvent {
    @Override
    public void createBookingIndex(BookingHistory bookingHistory) {

    }
}
