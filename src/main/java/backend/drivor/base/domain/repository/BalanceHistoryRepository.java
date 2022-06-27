package backend.drivor.base.domain.repository;

import backend.drivor.base.domain.document.BalanceHistory;
import org.springframework.data.repository.CrudRepository;

public interface BalanceHistoryRepository extends CrudRepository<BalanceHistory, Long> {
}
