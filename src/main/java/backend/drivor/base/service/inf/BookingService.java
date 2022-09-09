package backend.drivor.base.service.inf;

import backend.drivor.base.domain.document.Account;
import backend.drivor.base.domain.request.AcceptBookingRequest;
import backend.drivor.base.domain.request.DriverArrivedRequest;
import backend.drivor.base.domain.request.NewBookingRequest;
import backend.drivor.base.domain.request.UpdateBookingRequest;
import backend.drivor.base.domain.response.BookingHistoryResponse;
import backend.drivor.base.domain.response.GeneralSubmitResponse;

import javax.transaction.Transactional;
import java.util.List;

public interface BookingService {
    @Transactional
    BookingHistoryResponse newBookingRequest(Account account, NewBookingRequest request);
    @Transactional
    GeneralSubmitResponse arrivedBookingRequest(Account account, DriverArrivedRequest request);
    @Transactional
    GeneralSubmitResponse acceptBookingRequest(Account account, AcceptBookingRequest request);

    @Transactional
    GeneralSubmitResponse deleteBookingRequest(List<Long> ids);
    @Transactional
    BookingHistoryResponse updateBookingRequest(Account account, UpdateBookingRequest request);
}
