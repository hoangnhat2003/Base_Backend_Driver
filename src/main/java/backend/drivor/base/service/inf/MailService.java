package backend.drivor.base.service.inf;

import org.springframework.scheduling.annotation.Async;

public interface MailService {

    @Async
    void send(String from, String[] to, String subject, String text);
}
