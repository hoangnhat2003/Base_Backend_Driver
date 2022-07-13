package backend.drivor.base.domain.components;

import backend.drivor.base.config.websocket.WebsocketClientEndpoint;
import backend.drivor.base.domain.document.Account;
import backend.drivor.base.domain.document.ChatAccount;
import backend.drivor.base.domain.handler.AsyncHandler;
import backend.drivor.base.domain.repository.ChatAccountRepository;
import backend.drivor.base.domain.utils.LoggerUtil;
import backend.drivor.base.domain.utils.ServiceExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.ContainerProvider;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;
import java.net.URI;
import java.net.URISyntaxException;

@Component
public class WebSocketConnect {

    private static final String TAG = WebSocketConnect.class.getSimpleName();

    private Session session = null;
    @Autowired
    private ChatAccountRepository chatAccountRepository;

    /**
     * Connect to Websocket Server
     *
     * @param chat_username
     * @param chat_password
     * @throws Exception
     */
    public void connect(String chat_username, String chat_password) throws Exception {
        URI uri = null;
        try {
            uri = new URI(String.format("ws://localhost:8099/chat/%s/%s", chat_username, chat_password));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        Session session = container.connectToServer(WebsocketClientEndpoint.class, uri);
        if(session == null) {
            LoggerUtil.e(TAG, "Failed to connect to web socket server. Session null");
        }
    }

    /**
     * Get chat account by account
     *
     * @param account
     * @return
     */
    public ChatAccount getChatAccountInfo(Account account) {
        ChatAccount chatAccount = chatAccountRepository.findByAccount(account);

        if (chatAccount == null) {
            throw ServiceExceptionUtils.accountNotFound();
        }

        return chatAccount;
    }

    /**
     * Send message async
     * @param message
     * @throws Exception
     */
    public void sendMessageAsync(String message) throws Exception {

        AsyncHandler.run(() -> {
            session.getBasicRemote().sendText(message);
            return null;
        });
    }
}
