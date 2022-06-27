package backend.drivor.base.domain.model;

import backend.drivor.base.domain.document.Vehicle;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
public class VehicleInfo {

    private String type;
    private String make;
    private Integer seats;


    public VehicleInfo(String type, String make, Integer seats) {
        this.type = type;
        this.make = make;
        this.seats = seats;
    }

    public VehicleInfo(Vehicle vehicle) {
        this(vehicle.getType().name(),
                vehicle.getMake(),
                vehicle.getSeats());
    }

}
