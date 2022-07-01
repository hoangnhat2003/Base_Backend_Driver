package backend.drivor.base.service.impl;

import backend.drivor.base.domain.components.BillingConfigurations;
import backend.drivor.base.domain.document.Account;
import backend.drivor.base.domain.repository.BookingHistoryRepository;
import backend.drivor.base.domain.repository.VehicleRepository;
import backend.drivor.base.domain.request.NewBookingRequest;
import backend.drivor.base.domain.response.BookingHistoryResponse;
import backend.drivor.base.service.inf.AccountService;
import backend.drivor.base.service.inf.AccountWalletService;
import backend.drivor.base.service.inf.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    private BookingHistoryRepository bookingHistoryRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountWalletService accountWalletService;

    @Autowired
    private BillingConfigurations billingConfigurations;

    @Override
    public BookingHistoryResponse newBookingRequest(Account account, NewBookingRequest request) {
        return null;
    }
}
