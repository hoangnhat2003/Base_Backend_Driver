package backend.drivor.base.service.impl;

import backend.drivor.base.config.rabbitmq.RabbitMQConfig;
import backend.drivor.base.domain.components.RabbitMQSender;
import backend.drivor.base.domain.constant.*;
import backend.drivor.base.domain.document.*;
import backend.drivor.base.domain.model.MqMessage;
import backend.drivor.base.domain.model.VehicleInfo;
import backend.drivor.base.domain.request.DriverArrivedRequest;
import backend.drivor.base.domain.request.NewBookingRequest;
import backend.drivor.base.domain.response.BookingHistoryResponse;
import backend.drivor.base.domain.response.GeneralSubmitResponse;
import backend.drivor.base.domain.utils.*;
import backend.drivor.base.service.ServiceBase;
import backend.drivor.base.service.inf.BookingService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.GeoUnit;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
public class BookingServiceImpl extends ServiceBase implements BookingService {

    @Autowired
    private RabbitMQSender messageSender;

    @Autowired
    private ModelMapper mapper;

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

            long distance = CastTypeUtils.toLong(request.getDistance());
            String distanceUnit = request.getDistance_unit();

            if (GeoUnit.KM.name().toLowerCase().equals(distanceUnit.toLowerCase()))
                amount = BigNumberCalculator.multiply(billingConfig.getPrice_per_km(), distance);
            else if (GeoUnit.M.name().toLowerCase().equals(distanceUnit.toLowerCase()))
                amount =  BigNumberCalculator.multiply(billingConfig.getPrice_per_m(), distance);
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
            bookingHistory.setCreateDate(new Date().getTime());
            bookingHistory = bookingHistoryRepository.save(bookingHistory);
            redisCache.setWithExpire(RedisConstant.PREFIX_BOOKING_REQUEST + ":" + bookingHistory.getRequestId(), GsonSingleton.getInstance().toJson(bookingHistory), (int) TimeUnit.MINUTES.toSeconds(30));

//            Send message to RabbitMQ
            MqMessage message = new MqMessage(RabbitMQConfig.EXCHANGE_BOOKING, RabbitMQConfig.QUEUE_BOOKING + "_INIT_INDEX" ,RabbitMQConfig.ROUTING_KEY_BOOKING + "_INIT_INDEX", bookingHistory);
            this.messageSender.send(message);

        } catch (Exception e) {
            if (accountWallet != null)
                accountWalletService.unlockBalance(accountWallet, amount);
            throw ServiceExceptionUtils.internalServerError();
        }

        BookingHistoryResponse response = mapper.map(bookingHistory, BookingHistoryResponse.class);

        return response;
    }

    @Override
    public GeneralSubmitResponse arrivedBookingRequest(Account account, DriverArrivedRequest request) {

        String requestId = request.getRequest_id();

        String dataFromCache = (String) redisCache.get(RedisConstant.PREFIX_BOOKING_REQUEST + ":" + requestId);

        BookingHistory bookingHistory = GsonSingleton.getInstance().fromJson(dataFromCache, BookingHistory.class);

        if (bookingHistory == null)
            throw ServiceExceptionUtils.invalidParam("request_id");

        if (!BookingHistoryStatus.ACCEPTED.equals(bookingHistory.getStatus()))
            return new GeneralSubmitResponse(false);

        if (!account.getId().equals(bookingHistory.getDriver_account_id()))
            throw ServiceExceptionUtils.unAuthorize();

       // Send message to RabbitMQ

        return null;
    }
}
