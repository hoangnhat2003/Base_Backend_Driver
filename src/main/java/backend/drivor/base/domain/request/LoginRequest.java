package backend.drivor.base.domain.request;

import backend.drivor.base.domain.annotation.Email;
import backend.drivor.base.domain.annotation.Password;
import lombok.Data;

@Data
public class LoginRequest {

    @Email
    private String email;

    @Password
    private String password;
}
