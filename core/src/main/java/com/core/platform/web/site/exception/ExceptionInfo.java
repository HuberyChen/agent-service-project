package com.core.platform.web.site.exception;

import com.core.utils.ExceptionUtils;

public class ExceptionInfo {
    private final String message;
    private final String stackTrace;

    public ExceptionInfo(String message, Throwable e) {
        this.message = message;
        stackTrace = ExceptionUtils.stackTrace(e);
    }

    public String getMessage() {
        return message;
    }

    public String getStackTrace() {
        return stackTrace;
    }
}
