package backend.drivor.base.domain.request;

import backend.drivor.base.domain.annotation.Email;
import backend.drivor.base.domain.annotation.Password;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class SignupRequest {

    @NotBlank
    @Size(min = 3, max = 20)
    private String username;

    @Email
    private String email;

    @Password
    private String password;

}
