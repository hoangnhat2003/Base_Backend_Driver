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
@Component
@ServerEndpoint(value = "/chat/{username}/{password}", decoders = AdminMessageDecoder.class, encoders = AdminMessageEncoder.class)
public class ChatWebSocket {

    private static final String TAG = ChatWebSocket.class.getSimpleName();

    @Autowired
    private XMPPFacade xmppFacade;

    @OnOpen
    public void open(Session session, @PathParam("username") String username, @PathParam("password") String password) {
        LoggerUtil.i(TAG, String.format("Successful connection, username = %s, password = %s", username, password));
        LoggerUtil.i(TAG, String.format("On open, session_id = %s", session.getId()));
        if(xmppFacade == null) {
            LoggerUtil.i(TAG, "xmppFacade null");
        }
        xmppFacade.startSession(session, username, password);
    }

    @OnMessage
    public void handleMessage(AdminMessage message, Session session) {
        LoggerUtil.i(TAG, String.format("message received from client, %s", message));
        LoggerUtil.i(TAG, String.format("On message, session_id = %s", session.getId()));
        if(xmppFacade == null) {
            LoggerUtil.i(TAG, "xmppFacade null");
        }
        xmppFacade.sendMessage(message, session);
    }

    @OnClose
    public void close(Session session) {
        LoggerUtil.i(TAG, String.format("On close, session_id = %s", session.getId()));
        LoggerUtil.i(TAG, "Connection close");
        if(xmppFacade == null) {
            LoggerUtil.i(TAG, "xmppFacade null");
        }
        xmppFacade.disconnect(session);
    }

    @OnError
    public void onError(Throwable e, Session session) {
        LoggerUtil.i(TAG, String.format("On error, session_id = %s", session.getId()));
        LoggerUtil.exception(TAG, (Exception) e);
        if(xmppFacade == null) {
            LoggerUtil.i(TAG, "xmppFacade null");
        }
        xmppFacade.disconnect(session);
    }
}
