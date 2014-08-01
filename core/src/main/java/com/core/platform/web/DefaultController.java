package com.core.platform.web;

import com.core.platform.context.Messages;
import org.springframework.context.i18n.LocaleContextHolder;

import javax.inject.Inject;
import java.util.Locale;

public class DefaultController {
    protected Messages messages;

    protected String getMessage(String messageKey, Object... args) {
        Locale locale = LocaleContextHolder.getLocale();
        return messages.getMessage(messageKey, args, locale);
    }

    @Inject
    public void setMessages(Messages messages) {
        this.messages = messages;
    }
}
