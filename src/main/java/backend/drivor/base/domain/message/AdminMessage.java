package backend.drivor.base.domain.message;

import lombok.Data;

import java.util.UUID;

@Data
public class AdminMessage<T> {

    private String msgId;
    private String name;
    private String type;
    private T data;
    private long time;

    public AdminMessage() {
        msgId = UUID.randomUUID().toString();
        time = System.currentTimeMillis();
    }

    public AdminMessage(String name, String type, T data) {
        this();
        this.name = name;
        this.type = type;
        this.data = data;
    }
}
