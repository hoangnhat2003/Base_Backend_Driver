package backend.drivor.base.config.xmpp;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "xmpp")
public class XMPPProperties {


    /**
     * The address of the server.
     */
    private String host;

    /**
     * The port to use (usually 5222).
     */
    private int port;

    /**
     * The XMPP domain is what follows after the '@' sign in XMPP addresses (JIDs).
     */
    private String domain;
}
