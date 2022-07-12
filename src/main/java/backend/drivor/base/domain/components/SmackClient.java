package backend.drivor.base.domain.components;

import backend.drivor.base.config.xmpp.XMPPMessageTransmitter;
import backend.drivor.base.config.xmpp.XMPPProperties;
import backend.drivor.base.domain.exception.XMPPGenericException;
import backend.drivor.base.domain.utils.LoggerUtil;
import backend.drivor.base.domain.utils.ServiceExceptionUtils;
import lombok.RequiredArgsConstructor;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.chat2.Chat;
import org.jivesoftware.smack.chat2.ChatManager;
import org.jivesoftware.smack.packet.Presence;
import org.jxmpp.jid.impl.JidCreate;
import org.jxmpp.stringprep.XmppStringprepException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.Session;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class SmackClient {

    private static final String TAG = SmackClient.class.getSimpleName();

    @Autowired
    private XMPPMessageTransmitter xmppMessageTransmitter;

    @Autowired
    private  XMPPProperties xmppProperties;

    public Optional<XMPPConnection> connect() {
        XMPPConnection connection;
        try {
            ConnectionConfiguration config=new ConnectionConfiguration(xmppProperties.getHost(),xmppProperties.getPort());
            config.setSecurityMode(ConnectionConfiguration.SecurityMode.required);
            config.setSendPresence(true);
            config.setServiceName(xmppProperties.getHost());
            config.setDebuggerEnabled(false);
            config.setSASLAuthenticationEnabled(true);

            connection = new XMPPConnection(config);
            connection.connect();

            if (connection.isConnected()) {
                LoggerUtil.i(TAG, String.format("Smack Message Client connected to server with host: {} and port: {}", xmppProperties.getHost(), xmppProperties.getPort()));
            }

            Presence presence = new Presence(Presence.Type.available, "Online, Update Priority", 24, Presence.Mode.available);
            connection.sendPacket(presence);
        }catch (XMPPException e) {
            LoggerUtil.e(TAG, e.getMessage());
            return Optional.empty();
        }catch (Exception e) {
            LoggerUtil.e(TAG, e.getMessage());
            return Optional.empty();
        }
        return Optional.of(connection);
    }

    public void login(XMPPConnection connection, String username, String password) {
        try {
            connection.login(username, password);
        } catch (XMPPException e) {
            LoggerUtil.exception(TAG, e);
            LoggerUtil.e(TAG,String.format("Login to XMPP server with user {} failed.", connection.getUser()));

            Object user = connection.getUser();
            throw ServiceExceptionUtils.connectionError(user == null ? "unknown" : user.toString());
        }
        LoggerUtil.i(TAG, String.format("User '{}' logged in.", connection.getUser()));
    }

    public void sendMessage(XMPPConnection connection, String message, String to) {
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

    public void addIncomingMessageListener(XMPPConnection connection, Session webSocketSession) {
        ChatManager chatManager = ChatManager.getInstanceFor(connection);
        chatManager.addIncomingListener((from, message, chat) -> xmppMessageTransmitter
                .sendResponse(message, webSocketSession));
        LoggerUtil.i(TAG, String.format("Incoming message listener for user '{}' added.", connection.getUser()));
    }

    public void disconnect(XMPPConnection connection) {
        connection.disconnect();
        LoggerUtil.i(TAG, "Smack Message Client disconnected");
    }
}
