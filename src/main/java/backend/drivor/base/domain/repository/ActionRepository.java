package backend.drivor.base.domain.repository;

import backend.drivor.base.domain.document.Action;
import org.springframework.data.repository.CrudRepository;

public interface ActionRepository extends CrudRepository<Action, Long> {
}
