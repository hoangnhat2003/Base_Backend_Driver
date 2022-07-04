package backend.drivor.base.domain.document;

import backend.drivor.base.domain.enums.VehicleType;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Table(name = "billing_config")
@Entity
public class BillingConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private VehicleType vehicleType;
    private Integer seats;
    private long price_per_km;
    private long price_per_m;
    private Long extend_price;
    private long price_per_hour;
}
