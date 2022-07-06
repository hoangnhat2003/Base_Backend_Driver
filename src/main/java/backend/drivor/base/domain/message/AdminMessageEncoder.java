package backend.drivor.base.domain.message;
import backend.drivor.base.domain.utils.GsonSingleton;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

public class AdminMessageEncoder implements Encoder.Text<AdminMessage> {

    @Override
    public String encode(AdminMessage message) {
       return GsonSingleton.getInstance().toJson(message);
    }

    @Override
    public void init(EndpointConfig config) {

    }

    @Override
    public void destroy() {

    }
}
