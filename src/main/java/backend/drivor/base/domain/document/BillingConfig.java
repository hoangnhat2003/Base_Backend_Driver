package backend.drivor.base.domain.document;

import backend.drivor.base.domain.enums.VehicleType;
import backend.drivor.base.domain.utils.PostgresEnumType;
import lombok.Data;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.util.Date;

@Data
@Table(name = "billing_config")
@Entity
@TypeDef(
        name = "e_vehicle_type",
        typeClass = PostgresEnumType.class
)
public class BillingConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    @Type(type = "e_vehicle_type")
    private VehicleType vehicleType;
    private Integer seats;
    private long price_per_km;
    private long price_per_m;
    private Long extend_price;
    private long price_per_hour;
}
