package backend.drivor.base.service.searchbooking.service;

import backend.drivor.base.config.elasticsearch.ElasticsearchConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.stereotype.Service;

@Service
public class BookingSearchService {

    @Autowired
    private ElasticsearchConfig esConfig;

    @Autowired
    private ElasticsearchOperations elasticsearchOperations;






}
