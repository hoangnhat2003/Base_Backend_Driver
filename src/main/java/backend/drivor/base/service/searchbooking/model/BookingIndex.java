package backend.drivor.base.service.searchbooking.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;

@Data
@Builder
@Document(
        indexName = "${elasticsearch.index}",
        createIndex = false
)@Setting(settingPath = "es_user_settings.json")
public class BookingIndex {

    @Id
    @Field(name = "booking_id",type = FieldType.Long)
    private Long id;

    @Field(name = "requestId", type = FieldType.Text)
    private String requestId;
    @Field(name = "passengerName", type = FieldType.Text)
    private String passengerName;

    @Field(name = "requesterAccountId", type = FieldType.Long)
    private Long requester_account_id;

    @Field(name = "requestedAt", type = FieldType.Long)
    private long requested_at;
    @Field(name = "distance", type = FieldType.Double)
    private Double distance;
    @Field(name = "distanceUnit", type = FieldType.Text)
    private String distance_unit;
    @Field(name = "hours", type = FieldType.Integer)
    private Integer hours;
    @Field(name = "amount", type = FieldType.Long)
    private long amount;
    @Field(name = "waitingFee", type = FieldType.Long)
    private long waiting_fee;
    @Field(name = "payType", type = FieldType.Text)
    private String pay_type; // cash/wallet,...
    @Field(name = "waitingPayType", type = FieldType.Text)
    private String waiting_pay_type;
    @Field(name = "status", type = FieldType.Text)
    private String status;
    @Field(name = "waitingFeeStatus", type = FieldType.Text)
    private String waiting_fee_status;
    @Field(name = "totalAmount", type = FieldType.Long)
    private long totalAmount;
    @Field(name = "driverAmount", type = FieldType.Long)
    private long driver_amount;
    @Field(name = "driverAccountId", type = FieldType.Long)
    private Long driver_account_id;
    @Field(name = "acceptedAt", type = FieldType.Long)
    private long accepted_at;
    @Field(name = "canceledAt", type = FieldType.Long)
    private long canceled_at;
    @Field(name = "expiredAt", type = FieldType.Long)
    private long expired_at;
    @Field(name = "arrivedAt", type = FieldType.Long)
    private long arrived_at;
    @Field(name = "startedAt", type = FieldType.Long)
    private long started_at;
    @Field(name = "finishedAt", type = FieldType.Long)
    private long finished_at;
    @Field(name = "canceledBy", type = FieldType.Long)
    private Long canceled_by;
    @Field(name = "canceledComment", type = FieldType.Text)
    private String canceled_comment;
    @Field(name = "billingStatus", type = FieldType.Text)
    private String billingStatus;
    @Field(name = "createDate", type = FieldType.Long)
    private Long createDate;
    @Field(name = "note", type = FieldType.Text)
    private String note;
}
