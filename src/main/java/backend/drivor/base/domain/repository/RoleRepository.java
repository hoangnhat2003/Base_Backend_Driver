package backend.drivor.base.domain.repository;

import backend.drivor.base.domain.document.Role;
import backend.drivor.base.domain.enums.ERole;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface RoleRepository extends CrudRepository<Role, Long> {

    Optional<Role> findByName(ERole name);
}
