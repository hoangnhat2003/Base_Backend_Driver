package backend.drivor.base.domain.exception;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TokenRefreshException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private String token;
    private String message;

    public String generateStringResponse() {
        return String.format("%s : %s", token, message);
    }


}