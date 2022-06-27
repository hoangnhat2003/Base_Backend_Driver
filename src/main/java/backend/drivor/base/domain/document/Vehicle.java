package backend.drivor.base.domain.document;
import backend.drivor.base.domain.enums.VehicleType;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Table(name = "vehicle")
@Entity
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long account_id;

    @Enumerated(EnumType.STRING)
    private VehicleType type;

    private String make;

    private Integer seats;

}
