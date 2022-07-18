package backend.drivor.base.domain.components;

import backend.drivor.base.domain.document.Account;
import backend.drivor.base.domain.document.ChatAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.Session;

@Component
public class SendMessageAsync {

    @Autowired
    private WebSocketConnect webSocketConnect;

    public void send(ChatAccount userAccount, String message) throws Exception {


        // Connect to websocket server
        Session session = webSocketConnect.connect(userAccount.getUsername(), userAccount.getPassword());

        // Send message
        webSocketConnect.sendMessageAsync(message, session);

    }
}
