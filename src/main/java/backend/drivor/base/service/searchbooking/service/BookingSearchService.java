package backend.drivor.base.service.searchbooking.service;

import backend.drivor.base.config.elasticsearch.ElasticsearchConfig;
import backend.drivor.base.service.searchbooking.model.BookingSearchResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BookingSearchService {

    @Autowired
    private ElasticsearchConfig esConfig;

    @Autowired
    private ElasticsearchOperations elasticsearchOperations;

    public BookingSearchResponse search(Optional<String> passengerName,
                                        Optional<String> status,
                                        Optional<String> billingStatus,
                                        Optional<Integer[]> hours,
                                        Integer pageNo,
                                        Integer pageSize
                                        ) {

    }






}
