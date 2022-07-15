package backend.drivor.base.domain.utils;

import backend.drivor.base.domain.message.AdminMessage;
import org.springframework.stereotype.Component;

import javax.websocket.EncodeException;
import javax.websocket.Session;
import java.io.IOException;

@Component
public class WebSocketTextMessageHelper {

    private static final String TAG = WebSocketTextMessageHelper.class.getSimpleName();

    public void send(Session session, AdminMessage websocketMessage) {
        try {
            session.getBasicRemote().sendObject(websocketMessage);
        } catch (IOException | EncodeException e) {
            LoggerUtil.e(TAG, String.format("WebSocket error, message {} was not sent.", websocketMessage.toString()));
            LoggerUtil.exception(TAG, e);
        }
    }
}
