package backend.drivor.base.domain.request;

import backend.drivor.base.domain.annotation.Password;
import lombok.Data;

@Data
public class ChangePasswordRequest {

    private String current_password;

    @Password
    private String new_password;
}
