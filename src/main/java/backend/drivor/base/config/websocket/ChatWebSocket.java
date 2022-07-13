package backend.drivor.base.config.websocket;

import backend.drivor.base.domain.message.AdminMessage;
import backend.drivor.base.domain.message.AdminMessageDecoder;
import backend.drivor.base.domain.message.AdminMessageEncoder;
import backend.drivor.base.domain.utils.LoggerUtil;
import backend.drivor.base.facade.XMPPFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "/chat/{username}/{password}", decoders = AdminMessageDecoder.class, encoders = AdminMessageEncoder.class)
@Component
public class ChatWebSocket {

    private static final String TAG = ChatWebSocket.class.getSimpleName();

    @Autowired
    private XMPPFacade xmppFacade;

    @OnOpen
    public void open(Session session, @PathParam("username") String username, @PathParam("password") String password) {
        LoggerUtil.i(TAG, String.format("Successful connection, username = {}, password = {}", username, password));
        xmppFacade.startSession(session, username, password);
    }

    @OnMessage
    public void handleMessage(AdminMessage message, Session session) {
        LoggerUtil.i(TAG, String.format("message received from client, {}", message));
        xmppFacade.sendMessage(message, session);
    }

    @OnClose
    public void close(Session session) {
        LoggerUtil.i(TAG, "Connection close");
        xmppFacade.disconnect(session);
    }

    @OnError
    public void onError(Throwable e, Session session) {
        LoggerUtil.exception(TAG, (Exception) e);
        xmppFacade.disconnect(session);
    }


}
