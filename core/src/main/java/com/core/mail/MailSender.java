package com.core.mail;

import com.core.platform.web.url.TimeLength;
import com.core.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.List;

public class MailSender {
    private final Logger logger = LoggerFactory.getLogger(MailSender.class);
    private final JavaMailSenderImpl sender = new JavaMailSenderImpl();

    public MailSender() {
        sender.setDefaultEncoding("UTF-8");
    }

    public void send(Mail mail) {
        try {
            MimeMessage message = createMimeMessage(mail);
            logger.debug("start sending email");
            sender.send(message);
        } catch (MessagingException e) {
            throw new MailException(e);
        } finally {
            logger.debug("finish sending email");
        }
    }

    private MimeMessage createMimeMessage(Mail mail) throws MessagingException {
        MimeMessageHelper message = new MimeMessageHelper(sender.createMimeMessage());
        logger.debug("subject={}", mail.getSubject());
        message.setSubject(mail.getSubject());
        logger.debug("from={}", mail.getFrom());
        message.setFrom(mail.getFrom());
        logger.debug("to={}", mail.getToAddresses());
        message.setTo(toAddressArray(mail.getToAddresses()));
        logger.debug("cc={}", mail.getCCAddresses());
        message.setCc(toAddressArray(mail.getCCAddresses()));
        logger.debug("bcc={}", mail.getBCCAddresses());
        message.setBcc(toAddressArray(mail.getBCCAddresses()));
        message.setText(mail.getBody(), Mail.CONTENT_TYPE_HTML.equals(mail.getContentType()));
        setReplyTo(mail, message);
        return message.getMimeMessage();
    }

    void setReplyTo(Mail mail, MimeMessageHelper message) throws MessagingException {
        if (StringUtils.hasText(mail.getReplyTo())) {
            message.setReplyTo(mail.getReplyTo());
        } else {
            message.setReplyTo(mail.getFrom());
        }
    }

    private String[] toAddressArray(List<String> addresses) {
        return addresses.toArray(new String[addresses.size()]);
    }

    public void setHost(String host) {
        if (StringUtils.hasText(host))
            sender.setHost(host);
    }

    public void setPort(Integer port) {
        if (port != null)
            sender.setPort(port);
    }

    public void setUsername(String username) {
        if (StringUtils.hasText(username)) {
            sender.setUsername(username);
            sender.getJavaMailProperties().put("mail.smtp.auth", "true");
        }
    }

    public void setPassword(String password) {
        if (StringUtils.hasText(password))
            sender.setPassword(password);
    }

    public void setTimeout(TimeLength timeout) {
        if (timeout != null)
            sender.getJavaMailProperties().put("mail.smtp.timeout", timeout.toMilliseconds());
    }

}
