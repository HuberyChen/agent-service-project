package com.core.platform.exception;

import com.core.log.ActionLog;
import com.core.log.ActionLoggerImpl;
import com.core.log.ActionResult;
import com.core.platform.monitor.exception.ExceptionMonitor;
import com.core.platform.monitor.exception.RecentExceptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.UnsatisfiedServletRequestParameterException;

import javax.inject.Inject;

public class ErrorHandler {
    private final Logger logger = LoggerFactory.getLogger(ErrorHandler.class);

    private ExceptionMonitor exceptionMonitor;

    private RecentExceptions recentExceptions;

    private ActionLoggerImpl actionLogger;

    public void handle(Throwable e) {
        if (isIgnore(e))
            return;
        if (isWarning(e)) {
            logWarning(e);
            exceptionMonitor.warn(e);
        } else {
            logError(e);
            exceptionMonitor.error(e);
            recentExceptions.record(e);
        }
    }

    boolean isIgnore(Throwable e) {
        if (null == e)
            return true;
        return e.getClass().isAnnotationPresent(Ignore.class);
    }

    boolean isWarning(Throwable e) {
        return e.getClass().isAnnotationPresent(Warning.class) || isWarningException(e);
    }

    private boolean isWarningException(Throwable e) {
        return e instanceof HttpRequestMethodNotSupportedException || e instanceof BindException || e instanceof UnsatisfiedServletRequestParameterException
                || e instanceof MissingServletRequestParameterException || e instanceof ServletRequestBindingException || e instanceof MethodArgumentNotValidException;
    }

    private void logError(Throwable e) {
        ActionLog actionLog = actionLogger.currentActionLog();
        actionLog.setResult(ActionResult.ERROR);
        logger.error(e.getMessage(), e);
        actionLog.setException(e.getClass().getName());
        actionLog.setErrorMessage(e.getMessage());
    }

    private void logWarning(Throwable e) {
        ActionLog actionLog = actionLogger.currentActionLog();
        actionLog.setResult(ActionResult.WARNING);
        logger.info(e.getMessage(), e);
        actionLog.setException(e.getClass().getName());
        actionLog.setErrorMessage(e.getMessage());
    }

    @Inject
    public void setExceptionMonitor(ExceptionMonitor exceptionMonitor) {
        this.exceptionMonitor = exceptionMonitor;
    }

    @Inject
    public void setActionLogger(ActionLoggerImpl actionLogger) {
        this.actionLogger = actionLogger;
    }

    @Inject
    public void setRecentExceptions(RecentExceptions recentExceptions) {
        this.recentExceptions = recentExceptions;
    }
}
