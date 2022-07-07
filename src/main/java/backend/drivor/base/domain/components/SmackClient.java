package backend.drivor.base.domain.components;

import backend.drivor.base.domain.exception.XMPPGenericException;
import backend.drivor.base.domain.utils.LoggerUtil;
import backend.drivor.base.domain.utils.ServiceExceptionUtils;
import lombok.RequiredArgsConstructor;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.chat2.Chat;
import org.jivesoftware.smack.chat2.ChatManager;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.jxmpp.jid.EntityBareJid;
import org.jxmpp.jid.EntityFullJid;
import org.jxmpp.jid.impl.JidCreate;
import org.jxmpp.stringprep.XmppStringprepException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.Session;
import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class SmackClient {

    private static final String TAG = SmackClient.class.getSimpleName();

    @Autowired
    private XMPPMessageTransmitter xmppMessageTransmitter;

    private final XMPPProperties xmppProperties;

    public Optional<XMPPTCPConnection> connect(String username, String plainTextPassword) {
        XMPPTCPConnection connection;
        try {
            EntityBareJid entityBareJid;
            entityBareJid = JidCreate.entityBareFrom(username + "@" + xmppProperties.getDomain());
            XMPPTCPConnectionConfiguration config = XMPPTCPConnectionConfiguration.builder()
                    .setHost(xmppProperties.getHost())
                    .setPort(xmppProperties.getPort())
                    .setXmppDomain(xmppProperties.getDomain())
                    .setUsernameAndPassword(entityBareJid.getLocalpart(), plainTextPassword)
                    .setSecurityMode(ConnectionConfiguration.SecurityMode.disabled)
                    .setResource(entityBareJid.getResourceOrEmpty())
                    .setSendPresence(true)
                    .build();

            connection = new XMPPTCPConnection(config);
            connection.connect();
        }catch ( IOException | XMPPException e) {
            LoggerUtil.e(TAG, e.getMessage());
            return Optional.empty();
        }catch (Exception e) {
            LoggerUtil.e(TAG, e.getMessage());
            return Optional.empty();
        }
        return Optional.of(connection);
    }

    public void login(XMPPTCPConnection connection) {
        try {
            connection.login();
        } catch (XMPPException | SmackException | IOException | InterruptedException e) {
            LoggerUtil.exception(TAG, e);
            LoggerUtil.e(TAG,String.format("Login to XMPP server with user {} failed.", connection.getUser()));

            EntityFullJid user = connection.getUser();
            throw ServiceExceptionUtils.connectionError(user == null ? "unknown" : user.toString());
        }
        LoggerUtil.i(TAG, String.format("User '{}' logged in.", connection.getUser()));
    }

    public void sendMessage(XMPPTCPConnection connection, String message, String to) {
        ChatManager chatManager = ChatManager.getInstanceFor(connection);
        try {
            Chat chat = chatManager.chatWith(JidCreate.entityBareFrom(to + "@" + xmppProperties.getDomain()));
            chat.send(message);
            LoggerUtil.i(TAG, String.format("Message sent to user '{}' from user '{}'.", to, connection.getUser()));
        } catch (XmppStringprepException | SmackException.NotConnectedException | InterruptedException e) {
            LoggerUtil.exception(TAG,e);
            throw new XMPPGenericException(connection.getUser().toString());
        }
    }

    public void addIncomingMessageListener(XMPPTCPConnection connection, Session webSocketSession) {
        ChatManager chatManager = ChatManager.getInstanceFor(connection);
        chatManager.addIncomingListener((from, message, chat) -> xmppMessageTransmitter
                .sendResponse(message, webSocketSession));
        LoggerUtil.i(TAG, String.format("Incoming message listener for user '{}' added.", connection.getUser()));
    }

    public void disconnect(XMPPTCPConnection connection) {
        connection.disconnect();
        LoggerUtil.i(TAG, "Smack Message Client disconnected");
    }



}
