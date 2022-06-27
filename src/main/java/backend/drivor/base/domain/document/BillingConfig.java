package backend.drivor.base.domain.document;

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

    private String vehicle_type;
    private Integer seats;
    private long price_per_km;
    private long price_per_m;
    private long extend_price;
    private long price_per_hour;
}
