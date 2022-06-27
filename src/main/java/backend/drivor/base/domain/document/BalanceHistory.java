package backend.drivor.base.domain.document;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Table(name = "balance_history")
@Entity
public class BalanceHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String txref_id;
    private int type;
    private long amount;
    private String currency;
    private String wallet_type;
    private String wallet_id;
    private int operation;
    private String note;
}
