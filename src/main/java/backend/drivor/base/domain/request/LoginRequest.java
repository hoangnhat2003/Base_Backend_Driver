package backend.drivor.base.domain.request;

import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.NotBlank;

@Data
public class LoginRequest {

    @NonNull
    @NotBlank
    private String username;

    @NonNull
    @NotBlank
    private String password;
}
