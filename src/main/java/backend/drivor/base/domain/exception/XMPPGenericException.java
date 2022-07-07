package backend.drivor.base.domain.exception;

public class XMPPGenericException extends RuntimeException{

    private static final String MESSAGE = "Something went wrong when connecting to the XMPP server with username '%s'.";

    private static final long serialVersionUID = 1L;

    private String username;

    public XMPPGenericException(String username) {
       username = username;
    }

    public String generateStringResponse() {
        return String.format("%s : %s", MESSAGE, username);
    }
}
