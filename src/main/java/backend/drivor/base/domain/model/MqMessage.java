package backend.drivor.base.domain.model;

import jdk.jfr.DataAmount;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MqMessage {

    private String exchange;

    private String routingKey;

    private Object message;
}
