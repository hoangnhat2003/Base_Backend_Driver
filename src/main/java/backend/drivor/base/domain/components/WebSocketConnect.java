package backend.drivor.base.domain.components;

import backend.drivor.base.config.websocket.WebsocketClientEndpoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.net.URI;
import java.net.URISyntaxException;

@Component
public class WebSocketConnect {

    @Autowired
    private WebsocketClientEndpoint websocketClientEndpoint;

    @PostConstruct
    public void connect() {
        URI uri;
        try {
            uri = new URI("ws://localhost:8080/chat/");
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return;
        }
        websocketClientEndpoint = new WebsocketClientEndpoint(uri);

        // add listener
        websocketClientEndpoint.addMessageHandler(new WebsocketClientEndpoint.MessageHandler() {
            public void handleMessage(String message) {
                System.out.println(message);
            }
        });

    }






}
