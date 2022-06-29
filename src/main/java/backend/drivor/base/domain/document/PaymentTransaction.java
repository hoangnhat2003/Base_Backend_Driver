package backend.drivor.base.domain.document;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Table(name = "payment_transaction")
@Entity
public class PaymentTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String account_id;

    private String payment_transaction_id;

    private long amount;
    /**
     * Fee
     */
    private long payment_fee;

    /**
     * Store transaction's type.
     */
    private String type;


    private String status;

    private String payment_id;

    private String date;

    private String payment_method_type;

    private String payment_type;

    @Transient
    private Object payment_details;


}
