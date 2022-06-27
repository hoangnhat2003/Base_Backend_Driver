package backend.drivor.base.domain.document;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Table(name = "action")
@Entity
public class Action {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String action;
    private Date createTime;
}
