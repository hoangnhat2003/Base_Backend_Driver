package backend.drivor.base.domain.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatAccountResponse {

    private Long id;

    private Long accountId;

    private String chat_username;

    private String chat_password;
}
