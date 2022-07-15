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
    @Autowired
    private ChatAccountRepository chatAccountRepository;

    /**
     * Connect to Websocket Server
     *
     * @param chat_username
     * @param chat_password
     * @throws Exception
     */
    public Session connect(String chat_username, String chat_password) throws Exception {
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

        return session;
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
     *
     * @param message
     * @param session
     * @throws Exception
     */
    public void sendMessageAsync(String message, Session session) throws Exception {

        AsyncHandler.run(() -> {
            LoggerUtil.i(TAG, String.format("Start send message with session id = %s", session.getId()));
            session.getBasicRemote().sendText(message);
            LoggerUtil.i(TAG, String.format("Finished send message with session id = %s", session.getId()));
            return null;
        });
    }
}
