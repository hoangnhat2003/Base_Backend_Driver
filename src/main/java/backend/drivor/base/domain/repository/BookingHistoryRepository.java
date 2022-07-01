package backend.drivor.base.domain.repository;

import backend.drivor.base.domain.document.BookingHistory;
import org.springframework.data.repository.CrudRepository;

public interface BookingHistoryRepository extends CrudRepository<BookingHistory, Long> {
    BookingHistory findByRequestId(String requestId);
}
