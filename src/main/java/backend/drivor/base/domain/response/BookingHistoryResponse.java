package backend.drivor.base.domain.response;

import backend.drivor.base.domain.document.Account;
import backend.drivor.base.domain.document.BookingHistory;
import backend.drivor.base.domain.model.BookingLocation;
import backend.drivor.base.domain.model.VehicleInfo;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@NoArgsConstructor
public class BookingHistoryResponse {
    private Long id;
    private String requestId;

    private AccountShortInfoResponse requester_info;

    private Long requester_account_id;

    private VehicleInfo vehicle;
    private long requested_at;

    private BookingLocation from;

    private BookingLocation to;
    private Double distance;
    private String distance_unit;
    private Integer hours;
    private long amount; // satoshi
    private long waiting_fee;
    private String pay_type; // cash/wallet,...
    private String waiting_pay_type;
    private String status;
    private String waiting_fee_status;
    private long total_amount;
    private long driver_amount;
    private Long driver_account_id;
    private AccountShortInfoResponse driver_info;

    private long accepted_at;
    private long canceled_at;
    private long expired_at;
    private long arrived_at;
    private long started_at;
    private long finished_at;
    private Long canceled_by;
    private String canceled_comment;

    private boolean billing_updated;
    private String billing_status;
    private String billing_transaction_id;
    private Date createDate;
    private String note;

    public BookingHistoryResponse(Long id, String requestId, Long requester_account_id, AccountShortInfoResponse accountShortInfoResponse, VehicleInfo vehicle, long requested_at, BookingLocation from, BookingLocation to, Double distance, String distance_unit, long amount ,String pay_type, String status, Long driver_account_id, AccountShortInfoResponse accountShortInfoResponse1, long accepted_at, long canceled_at, long started_at, long finished_at, Long canceled_by, String canceled_comment, boolean billing_updated, String billing_status, String billing_transaction_id, Integer hours, String note, long arrived_at, long waiting_fee ,String waiting_pay_type, long expired_at) {
        this.id = id;
        this.requestId = requestId;
        this.requester_account_id = requester_account_id;
        this.requester_info = requester_info;
        this.vehicle = vehicle;
        this.requested_at = requested_at;
        this.from = from;
        this.to = to;
        this.distance = distance;
        this.distance_unit = distance_unit;
        this.amount = amount;
        this.pay_type = pay_type;
        this.status = status;
        this.driver_account_id = driver_account_id;
        this.driver_info = driver_info;
        this.accepted_at = accepted_at;
        this.canceled_at = canceled_at;
        this.started_at = started_at;
        this.finished_at = finished_at;
        this.canceled_by = canceled_by;
        this.canceled_comment = canceled_comment;
        this.billing_updated = billing_updated;
        this.billing_status = billing_status;
        this.billing_transaction_id = billing_transaction_id;
        this.hours = hours;
        this.note = note;
        this.arrived_at = arrived_at;
        this.waiting_fee = waiting_fee;
        this.waiting_pay_type = waiting_pay_type;
        this.expired_at = expired_at;
    }


    public BookingHistoryResponse(BookingHistory history, @NonNull Account requester, @NonNull Account driver) {
        this(history.getId(),
                history.getRequestId(),
                history.getRequester_account_id(),
                new AccountShortInfoResponse(requester),
                history.getVehicle(),
                history.getRequested_at(),
                history.getFrom(),
                history.getTo(),
                history.getDistance(),
                history.getDistance_unit(),
                history.getAmount(),
                history.getPay_type(),
                history.getStatus(),
                history.getDriver_account_id(),
                new AccountShortInfoResponse(driver),
                history.getAccepted_at(),
                history.getCanceled_at(),
                history.getStarted_at(),
                history.getFinished_at(),
                history.getCanceled_by(),
                history.getCanceled_comment(),
                history.isBilling_updated(),
                history.getBilling_status(),
                history.getBilling_transaction_id(),
                history.getHours(),
                history.getNote(),
                history.getArrived_at(),
                history.getWaiting_fee(),
                history.getWaiting_pay_type(),
                history.getExpired_at());
    }

}
