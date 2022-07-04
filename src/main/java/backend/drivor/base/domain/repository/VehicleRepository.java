package backend.drivor.base.domain.repository;

import backend.drivor.base.domain.document.Vehicle;
import org.springframework.data.repository.CrudRepository;

public interface VehicleRepository extends CrudRepository<Vehicle, Long> {
    Vehicle findById(String vehicle_id);
}
