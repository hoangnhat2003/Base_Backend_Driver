package backend.drivor.base.domain.message;

import backend.drivor.base.domain.response.BookingHistoryResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
public class AdminMessage<T> {

    private String msgId;

    private String from;
    private String to;
    private String name;
    private String type;
    private T data;
    private long time;

    public AdminMessage() {
        msgId = UUID.randomUUID().toString();
        time = System.currentTimeMillis();
    }

    public AdminMessage(String from, String to, String name, String type, T data) {
        this();
        this.from = from;
        this.to = to;
        this.name = name;
        this.type = type;
        this.data = data;
    }
}
