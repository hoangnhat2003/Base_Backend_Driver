package backend.drivor.base.service;

import backend.drivor.base.domain.components.BillingConfigurations;
import backend.drivor.base.domain.components.RedisCache;
import backend.drivor.base.domain.repository.AccountRepository;
import backend.drivor.base.domain.repository.AccountWalletRepository;
import backend.drivor.base.domain.repository.BookingHistoryRepository;
import backend.drivor.base.domain.repository.VehicleRepository;
import backend.drivor.base.service.inf.AccountService;
import backend.drivor.base.service.inf.AccountWalletService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

public abstract class ServiceBase {

    @Autowired
    protected PasswordEncoder passwordEncoder;

    @Autowired
    protected RedisCache redisCache;

    @Autowired
    protected BookingHistoryRepository bookingHistoryRepository;

    @Autowired
    protected VehicleRepository vehicleRepository;

    @Autowired
    protected AccountService accountService;

    @Autowired
    protected AccountWalletService accountWalletService;

    @Autowired
    protected BillingConfigurations billingConfigurations;

    @Autowired
    protected AccountWalletRepository accountWalletRepository;

    @Autowired
    protected AccountRepository accountRepository;
}
