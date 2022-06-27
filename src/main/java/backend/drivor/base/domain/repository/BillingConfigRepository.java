package backend.drivor.base.domain.repository;

import backend.drivor.base.domain.document.BillingConfig;
import org.springframework.data.repository.CrudRepository;

public interface BillingConfigRepository extends CrudRepository<BillingConfig, Long> {
}
