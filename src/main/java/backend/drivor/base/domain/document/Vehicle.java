package backend.drivor.base.domain.document;
import backend.drivor.base.domain.enums.VehicleType;
import backend.drivor.base.domain.utils.PostgresEnumType;
import lombok.Data;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.util.Date;

@Data
@Table(name = "vehicle")
@Entity
@TypeDef(
        name = "e_vehicle_type",
        typeClass = PostgresEnumType.class
)
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long account_id;

    @Enumerated(EnumType.STRING)
    @Type(type = "e_vehicle_type")
    private VehicleType type;

    private String make;

    private Integer seats;

}
