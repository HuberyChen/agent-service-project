package com.hubery.agent.service;

import com.quidsi.core.log.LogMessageFilter;

/**
 * @author neo
 */
public class agent-service-projectServiceLogMessageFilter extends LogMessageFilter {
    @Override
    public String filter(String loggerName, String message) {
        return message;
    }
}
