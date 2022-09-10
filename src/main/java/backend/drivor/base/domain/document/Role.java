package backend.drivor.base.domain.document;

import backend.drivor.base.domain.enums.ERole;
import backend.drivor.base.domain.utils.PostgresEnumType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.util.Date;

@Data
@Table(name = "roles")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@TypeDef(
        name = "e_roles",
        typeClass = PostgresEnumType.class
)
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    @Type(type = "e_roles")
    private ERole name;
}
