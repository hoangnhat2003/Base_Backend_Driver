package backend.drivor.base.domain.repository;

import backend.drivor.base.domain.document.PaymentTransaction;
import org.springframework.data.repository.CrudRepository;

public interface PaymentTransactionRepository extends CrudRepository<PaymentTransaction, Long> {
}
