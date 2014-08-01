package com.core.platform.web.site.session.provider;


import com.core.platform.monitor.ServiceMonitor;

public interface SessionProvider extends ServiceMonitor {
    String getAndRefreshSession(String sessionId);

    void saveSession(String sessionId, String sessionData);

    void clearSession(String sessionId);
}
