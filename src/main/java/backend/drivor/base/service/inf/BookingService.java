package backend.drivor.base.service.inf;

import backend.drivor.base.domain.document.Account;
import backend.drivor.base.domain.request.AcceptBookingRequest;
import backend.drivor.base.domain.request.DriverArrivedRequest;
import backend.drivor.base.domain.request.NewBookingRequest;
import backend.drivor.base.domain.response.BookingHistoryResponse;
import backend.drivor.base.domain.response.GeneralSubmitResponse;

public interface BookingService {

    BookingHistoryResponse newBookingRequest(Account account, NewBookingRequest request);

    GeneralSubmitResponse arrivedBookingRequest(Account account, DriverArrivedRequest request);

    GeneralSubmitResponse acceptBookingRequest(Account account, AcceptBookingRequest request);
}
