package backend.drivor.base.service.impl;

import backend.drivor.base.domain.exception.ServiceException;
import backend.drivor.base.domain.utils.LoggerUtil;
import backend.drivor.base.service.inf.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailServiceImpl implements MailService {

    private static final String TAG = MailServiceImpl.class.getSimpleName();

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void send(String from, String[] to, String subject, String text) {

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(from);
            message.setTo(to);
            message.setSubject(subject);
            message.setText(text);
            mailSender.send(message);
            LoggerUtil.i(TAG, "Send email success");
        }catch (Exception e) {
            LoggerUtil.exception(TAG, e);
            throw new ServiceException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
