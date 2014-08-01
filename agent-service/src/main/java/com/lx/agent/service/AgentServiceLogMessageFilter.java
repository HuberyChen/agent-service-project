package com.lx.agent.service;


import com.core.log.LogMessageFilter;

public class AgentServiceLogMessageFilter extends LogMessageFilter {
    @Override
    public String filter(String loggerName, String message) {
        return message;
    }
}
