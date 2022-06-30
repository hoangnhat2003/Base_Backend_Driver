package backend.drivor.base.domain.components;

import backend.drivor.base.domain.document.BillingConfig;
import backend.drivor.base.domain.document.Vehicle;
import backend.drivor.base.domain.enums.VehicleType;
import backend.drivor.base.domain.repository.BillingConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BillingConfigurations {

    @Autowired
    private BillingConfigRepository billingConfigRepository;

    public BillingConfig getConfig(Vehicle vehicle) {
          return _getConfig(vehicle);
    }

    private BillingConfig _getConfig(Vehicle vehicle) {

        BillingConfig billingConfig = null;

        if(vehicle.getType().equals(VehicleType.BIKE)) {
            billingConfig = billingConfigRepository.findByVehicleType(vehicle.getType());
        }

        if(vehicle.getType().equals(VehicleType.CAR)) {
            if(vehicle.getSeats() == null) {
                billingConfig = billingConfigRepository.findByVehicleType(vehicle.getType());
            }
            if(vehicle.getSeats() != null) {
                billingConfig = billingConfigRepository.findByVehicleTypeAndSeats(vehicle.getType(), vehicle.getSeats());
            }
        }
        return billingConfig;
    }

}
