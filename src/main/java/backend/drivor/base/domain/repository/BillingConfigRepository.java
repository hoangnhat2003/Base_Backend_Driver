package backend.drivor.base.domain.repository;

import backend.drivor.base.domain.document.BillingConfig;
import backend.drivor.base.domain.enums.VehicleType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface BillingConfigRepository extends CrudRepository<BillingConfig, Long> {

    BillingConfig findByVehicleTypeAndSeats(VehicleType type, Integer seats);

    BillingConfig findByVehicleType(VehicleType type);
}
