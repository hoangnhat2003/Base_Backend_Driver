package backend.drivor.base.config.xmpp;

import backend.drivor.base.domain.message.AdminMessage;
import backend.drivor.base.domain.utils.GsonSingleton;
import backend.drivor.base.domain.utils.LoggerUtil;
import backend.drivor.base.domain.utils.WebSocketTextMessageHelper;
import lombok.RequiredArgsConstructor;
import org.jivesoftware.smack.packet.Message;
import org.springframework.stereotype.Component;

import javax.websocket.Session;

@Component
@RequiredArgsConstructor
public class XMPPMessageTransmitter {

    private static final String TAG = XMPPMessageTransmitter.class.getSimpleName();

    private final WebSocketTextMessageHelper webSocketTextMessageHelper;

    public void sendResponse(Message message, Session session) {

        LoggerUtil.i(TAG, String.format("New message from '{}' to '{}': {}", message.getFrom(), message.getTo(), message.getBody()));
        String content = message.getBody();
        webSocketTextMessageHelper.send(
                session,
                GsonSingleton.getInstance().fromJson(content, AdminMessage.class)
        );
    }
}
