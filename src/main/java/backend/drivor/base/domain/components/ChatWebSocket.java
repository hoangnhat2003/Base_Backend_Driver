package backend.drivor.base.domain.components;

import backend.drivor.base.domain.message.AdminMessage;
import backend.drivor.base.domain.message.AdminMessageDecoder;

import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "/chat/{username}/{password}", decoders = AdminMessageDecoder.class)
public class ChatWebSocket {
}
