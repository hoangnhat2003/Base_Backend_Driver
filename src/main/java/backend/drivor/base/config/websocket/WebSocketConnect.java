package backend.drivor.base.config.websocket;

import backend.drivor.base.config.websocket.WebsocketClientEndpoint;
import backend.drivor.base.domain.document.Account;
import backend.drivor.base.domain.document.ChatAccount;
import backend.drivor.base.domain.handler.AsyncHandler;
import backend.drivor.base.domain.repository.ChatAccountRepository;
import backend.drivor.base.domain.response.ChatAccountResponse;
import backend.drivor.base.domain.utils.ServiceExceptionUtils;
import backend.drivor.base.service.inf.AccountService;
import org.glassfish.tyrus.client.ClientManager;
import org.jivesoftware.smack.Chat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.websocket.DeploymentException;
import javax.websocket.Session;
import java.net.URI;
import java.net.URISyntaxException;

@Component
public class WebSocketConnect {

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
        URI uri;
        try {
            uri = new URI(String.format("ws://localhost:8080/chat/%s/%s", chat_username, chat_password));
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return;
        }

        ClientManager client = ClientManager.createClient();

        session = client.connectToServer(WebsocketClientEndpoint.class, uri);
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
