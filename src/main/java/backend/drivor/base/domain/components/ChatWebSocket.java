package backend.drivor.base.domain.components;

import backend.drivor.base.domain.message.AdminMessage;
import backend.drivor.base.domain.message.AdminMessageDecoder;
import backend.drivor.base.domain.message.AdminMessageEncoder;

import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "/chat/{username}/{password}", decoders = AdminMessageDecoder.class, encoders = AdminMessageEncoder.class)
public class ChatWebSocket {
}
