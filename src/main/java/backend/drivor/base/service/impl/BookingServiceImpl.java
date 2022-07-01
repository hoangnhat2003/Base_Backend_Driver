package backend.drivor.base.service.impl;

import backend.drivor.base.domain.components.BillingConfigurations;
import backend.drivor.base.domain.components.RedisCache;
import backend.drivor.base.domain.constant.*;
import backend.drivor.base.domain.document.*;
import backend.drivor.base.domain.model.VehicleInfo;
import backend.drivor.base.domain.repository.BookingHistoryRepository;
import backend.drivor.base.domain.repository.VehicleRepository;
import backend.drivor.base.domain.request.NewBookingRequest;
import backend.drivor.base.domain.response.BookingHistoryResponse;
import backend.drivor.base.domain.utils.BigNumberCalculator;
import backend.drivor.base.domain.utils.ServiceExceptionUtils;
import backend.drivor.base.domain.utils.StringUtils;
import backend.drivor.base.service.ServiceBase;
import backend.drivor.base.service.inf.AccountService;
import backend.drivor.base.service.inf.AccountWalletService;
import backend.drivor.base.service.inf.BookingService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.GeoUnit;

import java.util.concurrent.TimeUnit;

@Service
public class BookingServiceImpl extends ServiceBase implements BookingService {


    @Override
    public BookingHistoryResponse newBookingRequest(Account account, NewBookingRequest request) {

        AccountWallet debtWallet = accountWalletService.getOrCreateAccountWallet(account, AccountWalletType.DEBT_WALLET);

        if (debtWallet != null && debtWallet.getBalance() > 0)
            throw ServiceExceptionUtils.accountOwing();

        String requestId = request.getRequest_id();

        BookingHistory bookingHistory = bookingHistoryRepository.findByRequestId(requestId);
        if (bookingHistory != null)
            throw ServiceExceptionUtils.valueExists("request_id");

        Integer hours = request.getHours();
        boolean bookByHour = hours != null && hours > 0;

        if (!bookByHour) {
            if (request.getDistance() == null)
                throw ServiceExceptionUtils.missingParam("distance");
            if (StringUtils.isEmpty(request.getDistance_unit()))
                throw ServiceExceptionUtils.missingParam("distance_unit");
        }

        if (StringUtils.hasText(request.getPay_type())) {
            if (!BookingPayType.CASH.equals(request.getPay_type())
                    && !BookingPayType.DRIVOR_WALLET.equals(request.getPay_type()))
                throw ServiceExceptionUtils.invalidParam("pay_type");
        }

        Vehicle vehicle = null;
        if (StringUtils.hasText(request.getVehicle_id()))
            vehicle = vehicleRepository.findById(Long.valueOf(request.getVehicle_id())).get();

        if (vehicle == null)
            throw ServiceExceptionUtils.vehicleNotFound();

        BillingConfig billingConfig = billingConfigurations.getConfig(vehicle);

        long amount = 0;
        if (bookByHour) {
            amount = BigNumberCalculator.multiply(billingConfig.getPrice_per_hour(), hours);
        }else {

            long distance = Long.valueOf(request.getDistance().toString());
            String distanceUnit = request.getDistance_unit();

            if (GeoUnit.KM.name().toLowerCase().equals(distanceUnit.toLowerCase()))
                amount = BigNumberCalculator.multiply(billingConfig.getPrice_per_km(), distance);
            else if (GeoUnit.M.name().toLowerCase().equals(distanceUnit.toLowerCase()))
                amount = BigNumberCalculator.multiply(billingConfig.getPrice_per_m(), distance);
        }

        String payType = StringUtils.hasText(request.getPay_type()) ? request.getPay_type() : BookingPayType.CASH;
        AccountWallet accountWallet = null;

        if (BookingPayType.DRIVOR_WALLET.equals(payType)) {
            accountWallet = accountWalletService.getOrCreateAccountWallet(account, AccountWalletType.DRIVOR_WALLET);
            if (accountWallet == null)
                throw ServiceExceptionUtils.walletNotFound();

            if (accountWallet.getBalance() < amount)
                throw ServiceExceptionUtils.notEnoughBalance();

            accountWalletService.lockBalance(accountWallet, amount);
        }

        try {
            bookingHistory = new BookingHistory();
            bookingHistory.setRequestId(requestId);
            bookingHistory.setRequester_account_id(account.getId());
            bookingHistory.setVehicle(new VehicleInfo(vehicle));
            bookingHistory.setRequested_at(System.currentTimeMillis());
            bookingHistory.setFrom(request.getFrom());
            bookingHistory.setTo(request.getTo());
            bookingHistory.setDistance(request.getDistance());
            bookingHistory.setDistance_unit(request.getDistance_unit());
            bookingHistory.setHours(hours);
            bookingHistory.setAmount(amount);
            bookingHistory.setPay_type(payType);
            bookingHistory.setStatus(BookingHistoryStatus.CREATED);
            bookingHistory.setBilling_status(BillingStatus.IN_PROCESS);
            bookingHistory.setNote(request.getNote());
            bookingHistory = bookingHistoryRepository.save(bookingHistory);
            redisCache.setWithExpire(RedisConstant.PREFIX_BOOKING_REQUEST + ":" + bookingHistory.getRequestId(), "", (int) TimeUnit.MINUTES.toSeconds(30));
        } catch (Exception e) {
            if (accountWallet != null)
                accountWalletService.unlockBalance(accountWallet, amount);
            throw ServiceExceptionUtils.internalServerError();
        }

        BookingHistoryResponse response = mapper.map(bookingHistory, BookingHistoryResponse.class);

        return response;
    }
}
