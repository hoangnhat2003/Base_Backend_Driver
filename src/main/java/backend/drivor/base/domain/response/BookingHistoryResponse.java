package backend.drivor.base.domain.response;

import backend.drivor.base.domain.model.BookingLocation;
import backend.drivor.base.domain.model.VehicleInfo;
import lombok.Data;

@Data
public class BookingHistoryResponse {

    private String request_id;
    private String requester_account_id;
    private VehicleInfo vehicle;
    private long requested_at;
    private BookingLocation from;
    private BookingLocation to;
    private Double distance;
    private String distance_unit;
    private String amount;
    private String price_currency;
    private String pay_type; // cash/wallet,...
    private String status;
    private String driver_account_id;
    private long accepted_at;
    private long canceled_at;
    private long started_at;
    private long finished_at;
    private boolean requester_rated;
    private boolean driver_rated;
    private String canceled_by;
    private String canceled_comment;
    private Integer hours;
    private String note;
    private long arrived_at;
    private String waiting_fee;
    private String waiting_pay_type;
    private long expired_at;
}
