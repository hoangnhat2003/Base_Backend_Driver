package backend.drivor.base.service.searchbooking.service;

import backend.drivor.base.config.elasticsearch.ElasticsearchConfig;
import backend.drivor.base.consumer.event.InitIndexEvent;
import backend.drivor.base.consumer.receiver.InitIndexReceiver;
import backend.drivor.base.domain.document.BookingHistory;
import backend.drivor.base.domain.utils.GsonSingleton;
import backend.drivor.base.domain.utils.LoggerUtil;
import backend.drivor.base.service.searchbooking.model.BookingIndex;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.stereotype.Component;

@Component
public class BookingIndexManager implements InitIndexEvent {

    private static final String TAG = BookingIndexManager.class.getSimpleName();


    @Autowired
    private ElasticsearchConfig esConfig;

    @Autowired
    private ElasticsearchOperations elasticsearchOperations;


    @Override
    public void createBookingIndex(BookingHistory bookingHistory) {

        LoggerUtil.i(TAG,"Booking data from queue : " + GsonSingleton.getInstance().toJson(bookingHistory));

        BookingIndex bookingIndex = BookingIndex.builder()
                .id(bookingHistory.getId())
                .accepted_at(bookingHistory.getAccepted_at())
                .amount(bookingHistory.getAmount())
                .passengerName(bookingHistory.getPassengerName())
                .arrived_at(bookingHistory.getArrived_at())
                .billingStatus(bookingHistory.getBilling_status())
                .canceled_at(bookingHistory.getCanceled_at())
                .createDate(bookingHistory.getCreateDate())
                .canceled_by(bookingHistory.getCanceled_by())
                .canceled_comment(bookingHistory.getCanceled_comment())
                .distance(bookingHistory.getDistance())
                .distance_unit(bookingHistory.getDistance_unit())
                .driver_account_id(bookingHistory.getDriver_account_id())
                .driver_amount(bookingHistory.getDriver_amount())
                .expired_at(bookingHistory.getExpired_at())
                .finished_at(bookingHistory.getFinished_at())
                .hours(bookingHistory.getHours())
                .note(bookingHistory.getNote())
                .pay_type(bookingHistory.getPay_type())
                .requested_at(bookingHistory.getRequested_at())
                .requester_account_id(bookingHistory.getRequester_account_id())
                .requestId(bookingHistory.getRequestId())
                .started_at(bookingHistory.getStarted_at())
                .status(bookingHistory.getStatus())
                .totalAmount(bookingHistory.getTotal_amount())
                .waiting_fee(bookingHistory.getWaiting_fee())
                .waiting_fee_status(bookingHistory.getWaiting_fee_status())
                .waiting_pay_type(bookingHistory.getWaiting_pay_type())
                .build();

        this.indexBooking(bookingIndex);
    }

    private void indexBooking(@NonNull BookingIndex bookingIndex) {
        String documentId = bookingIndex.getId().toString();
        IndexQuery indexQuery = new IndexQueryBuilder()
                .withId(documentId)
                .withObject(bookingIndex)
                .build();
        elasticsearchOperations.index(indexQuery, IndexCoordinates.of(esConfig.getIndex()));
    }
}
