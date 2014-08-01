package com.core.log;

import com.core.mail.MailSender;
import com.core.utils.StringUtils;

public class LogSettings {
    private static final LogSettings INSTANCE = new LogSettings();

    private boolean enableTraceLog = true;

    private boolean alwaysWriteTraceLog;

    private LogMessageFilter logMessageFilter;

    private MailSender errorEmailSender;

    private String[] errorNotificationEmails;

    private String errorEmailFromAddress;

    public static LogSettings get() {
        return INSTANCE;
    }

    public boolean isEnableTraceLog() {
        return enableTraceLog;
    }

    public void setEnableTraceLog(boolean enableTraceLog) {
        this.enableTraceLog = enableTraceLog;
    }

    public boolean isAlwaysWriteTraceLog() {
        return alwaysWriteTraceLog;
    }

    public void setAlwaysWriteTraceLog(boolean alwaysWriteTraceLog) {
        this.alwaysWriteTraceLog = alwaysWriteTraceLog;
    }

    public LogMessageFilter getLogMessageFilter() {
        return logMessageFilter;
    }

    public void setLogMessageFilter(LogMessageFilter logMessageFilter) {
        this.logMessageFilter = logMessageFilter;
    }

    public void enableEmailErrorNotification(String smtpHost) {
        if (StringUtils.hasText(smtpHost)) {
            errorEmailSender = new MailSender();
            errorEmailSender.setHost(smtpHost);
        }
    }

    public String[] getErrorNotificationEmails() {
        return errorNotificationEmails;
    }

    public void setErrorNotificationEmails(String errorNotificationEmails) {
        if (StringUtils.hasText(errorNotificationEmails))
            this.errorNotificationEmails = errorNotificationEmails.split(",");
    }

    public String getErrorEmailFromAddress() {
        return errorEmailFromAddress;
    }

    public void setErrorEmailFromAddress(String errorEmailFromAddress) {
        this.errorEmailFromAddress = errorEmailFromAddress;
    }

    public MailSender getErrorEmailSender() {
        return errorEmailSender;
    }
}
