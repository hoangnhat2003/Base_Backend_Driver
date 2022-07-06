package backend.drivor.base.domain.message;

import com.google.gson.Gson;

import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

public class AdminMessageDecoder implements Decoder.Text<AdminMessage> {

    @Override
    public AdminMessage decode(String message) {
        Gson gson = new Gson();
        return gson.fromJson(message, AdminMessage.class);
    }

    @Override
    public boolean willDecode(String message) {
        return (message != null);
    }

    @Override
    public void init(EndpointConfig config) {

    }

    @Override
    public void destroy() {

    }
}
