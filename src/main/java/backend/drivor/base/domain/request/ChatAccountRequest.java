package backend.drivor.base.domain.request;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatAccountRequest {

    @NonNull
    @NotBlank
    private String username;

    @NonNull
    @NotBlank
    private String password;
}
