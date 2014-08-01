package com.core.platform.web.site.cdn;

public interface CDNSettings {
    String[] getCDNHosts();

    boolean supportHTTPS();
}
