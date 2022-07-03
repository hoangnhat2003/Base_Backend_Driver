package backend.drivor.base.service.searchbooking.service;

import backend.drivor.base.config.elasticsearch.ElasticsearchConfig;
import backend.drivor.base.domain.document.BookingHistory;
import backend.drivor.base.domain.utils.CastTypeUtils;
import backend.drivor.base.domain.utils.LoggerUtil;
import backend.drivor.base.service.searchbooking.model.BookingIndex;
import backend.drivor.base.service.searchbooking.model.BookingSearchModel;
import backend.drivor.base.service.searchbooking.model.BookingSearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.WeekFields;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BookingSearchService {

    private static final String TAG = BookingSearchService.class.getSimpleName();

    @Autowired
    private ElasticsearchConfig esConfig;

    @Autowired
    private ElasticsearchOperations elasticsearchOperations;

    @Autowired
    private ModelMapper mapper;

    public BookingSearchResponse search(Optional<String> passengerName,
                                        Optional<String[]> statuses,
                                        Optional<String[]> billingStatus,
                                        Optional<String[]> amount,
                                        Optional<String> dateType,
                                        Integer pageNo,
                                        Integer pageSize) {

        List<BookingIndex> data = new ArrayList<>();

        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

        if (passengerName.isPresent()) {
            boolQueryBuilder.must(QueryBuilders.wildcardQuery("passengerName", "*" + passengerName.get() + "*"));
        }

        if (statuses.isPresent()) {
            for (int i = 0; i < statuses.get().length; i++) {
                String status = statuses.get()[i];
                boolQueryBuilder.must(QueryBuilders.termQuery("status", status));
            }
        }

        if (billingStatus.isPresent()) {
            for (int i = 0; i < billingStatus.get().length; i++) {
                String billing_status = billingStatus.get()[i];
                boolQueryBuilder.must(QueryBuilders.termQuery("billingStatus", billing_status));
            }
        }

        if (amount.isPresent() && amount.get().length > 0 && amount.get().length < 3) {
            Long min = CastTypeUtils.toLong(amount.get()[0]);
            Long max = CastTypeUtils.toLong(amount.get()[1]);

            boolQueryBuilder.must(QueryBuilders.rangeQuery("totalAmount").from(min).to(max));
        }

        Query query = new NativeSearchQueryBuilder()
                .withQuery(boolQueryBuilder)
                .withFilter(boolQueryBuilder)
                .withSearchType(SearchType.DEFAULT)
                .withSorts(SortBuilders.fieldSort("createdDate").order(SortOrder.DESC))
                .build();

        SearchHits<BookingIndex> searchHits = elasticsearchOperations.search(query, BookingIndex.class, IndexCoordinates.of(esConfig.getIndex()));

        if (searchHits.isEmpty()) {
            return new BookingSearchResponse();
        }

        try {
            data = searchHits.getSearchHits().stream()
                    .map(SearchHit::getContent)
                    .collect(Collectors.toList());

            if (data == null) {
                throw new Exception("Data from elasticsearch query is null");
            }
        } catch (Exception e) {
            LoggerUtil.e(TAG, e.getLocalizedMessage());
            throw new RuntimeException(e);
        }

        if (dateType.isPresent()) {
            if (Objects.equals("today", dateType)) {
                data = getBookingSearchResponsesByToday(data);
            }

            if (Objects.equals("week", dateType)) {
                data = getBookingSearchResponsesByWeekNow(data);
            }

            if (Objects.equals("month", dateType)) {
                data = getBookingSearchResponsesByMonth(data);
            }
        }

        List<BookingSearchModel> res = data.stream().map(item -> mapper.map(item, BookingSearchModel.class)).collect(Collectors.toList());

        PagedListHolder<BookingSearchModel> pagedListHolder = new PagedListHolder<BookingSearchModel>(res);
        pagedListHolder.setPage(pageNo);
        pagedListHolder.setPageSize(pageSize);

        BookingSearchResponse bookingSearchResponse = new BookingSearchResponse(pagedListHolder.getSource(), pagedListHolder.getNrOfElements(), pagedListHolder.getPageCount());

        return pagedListHolder != null ? bookingSearchResponse : new BookingSearchResponse();
    }

    private List<BookingIndex> getBookingSearchResponsesByToday(List<BookingIndex> bookingSearchResponses) {
        if (bookingSearchResponses != null) {
            List<Long> bookingTimes = bookingSearchResponses.stream().map(BookingIndex::getCreateDate).collect(Collectors.toList());
            if (bookingTimes != null) {
                LocalDate today = new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                int day = today.getDayOfMonth();
                List<BookingIndex> bookingSearchResponseList = new ArrayList<>();

                for (int i = 0; i < bookingTimes.size(); i++) {
                    Date date = new Date(bookingTimes.get(i));
                    LocalDate localDateOfT = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    int dayOfMonth = localDateOfT.getDayOfMonth();
                    if (dayOfMonth == day) {
                        bookingSearchResponseList.add(bookingSearchResponses.get(i));
                    }
                }
                if (!bookingSearchResponseList.isEmpty()) {
                    return bookingSearchResponseList;
                }
            }
        }
        return new ArrayList<>();
    }


    private List<BookingIndex> getBookingSearchResponsesByWeekNow(List<BookingIndex> bookingSearchResponses) {
        if (bookingSearchResponses != null) {
            List<Long> bookingTimes = bookingSearchResponses.stream().map(BookingIndex::getCreateDate).collect(Collectors.toList());
            if (bookingTimes != null) {
                LocalDate dateNow = new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                int weekOfYearNow = dateNow.get(WeekFields.of(DayOfWeek.MONDAY, 7).weekOfYear());
                List<BookingIndex> bookingSearchResponseList = new ArrayList<>();

                for (int i = 0; i < bookingTimes.size(); i++) {
                    Date date = new Date(bookingTimes.get(i));
                    LocalDate localDateOfT = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    int weekOfYear = localDateOfT.get(WeekFields.of(DayOfWeek.MONDAY, 7).weekOfYear());
                    if (weekOfYear == weekOfYearNow) {
                        bookingSearchResponseList.add(bookingSearchResponses.get(i));
                    }
                }
                if (!bookingSearchResponseList.isEmpty()) {
                    return bookingSearchResponseList;
                }
            }
        }
        return new ArrayList<>();
    }

    private List<BookingIndex> getBookingSearchResponsesByMonth(List<BookingIndex> bookingSearchResponses) {
        if (bookingSearchResponses != null) {
            List<Long> bookingTimes = bookingSearchResponses.stream().map(BookingIndex::getCreateDate).collect(Collectors.toList());
            if (bookingTimes != null) {
                LocalDate dateNow = new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                int monthNow = dateNow.getMonthValue();
                List<BookingIndex> bookingSearchResponseList = new ArrayList<>();
                for (int i = 0; i < bookingTimes.size(); i++) {
                    Date date = new Date(bookingTimes.get(i));
                    LocalDate localDateOfT = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    int month = localDateOfT.getMonthValue();
                    if (month == monthNow) {
                        bookingSearchResponseList.add(bookingSearchResponses.get(i));
                    }
                }
                if (!bookingSearchResponseList.isEmpty()) {
                    return bookingSearchResponseList;
                }
            }
        }
        return new ArrayList<>();
    }

}
