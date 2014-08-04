package com.core.log;

import com.core.mail.Mail;
import com.core.mail.MailSender;
import com.core.utils.NetworkUtils;

import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class EmailErrorNotifier {
    private static final Executor THREAD_POOL = new ThreadPoolExecutor(0, 5, 60L, TimeUnit.SECONDS, new LinkedBlockingDeque<Runnable>());
    private StringBuilder content = new StringBuilder();

    void addEvent(String line) {
        content.append(line);
    }

    void send() {
        THREAD_POOL.execute(new Runnable() {
            @Override
            public void run() {
                sendErrorNotification();
            }
        });
    }

    private void sendErrorNotification() {
        LogSettings settings = LogSettings.get();
        MailSender sender = settings.getErrorEmailSender();
        Mail mail = new Mail();
        for (String toAddress : settings.getErrorNotificationEmails()) {
            mail.addTo(toAddress);
        }
        mail.setFrom(settings.getErrorEmailFromAddress());
        mail.setSubject("[ALERT] exception occurred on " + NetworkUtils.localHostName());
        mail.setPlainTextBody(content.toString());
        content = null; // Thread pool will hold reference of 'this' object, clean up log content immediately
        sender.send(mail);
    }
}
