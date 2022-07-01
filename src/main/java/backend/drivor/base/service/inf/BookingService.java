package backend.drivor.base.service.inf;

import backend.drivor.base.domain.document.Account;
import backend.drivor.base.domain.request.NewBookingRequest;
import backend.drivor.base.domain.response.BookingHistoryResponse;

public interface BookingService {

    BookingHistoryResponse newBookingRequest(Account account, NewBookingRequest request);

}
