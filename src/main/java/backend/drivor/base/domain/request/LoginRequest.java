package backend.drivor.base.domain.request;

import backend.drivor.base.domain.annotation.Email;
import backend.drivor.base.domain.annotation.Password;
import lombok.Data;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest implements Serializable {


    @NonNull
    @NotBlank
    private String username;

    @Password
    private String password;
}
