package backend.drivor.base.service.searchbooking.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingSearchModel {
    private Long id;
    private String requestId;
    private Long requester_account_id;

    private long requested_at;

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
}
