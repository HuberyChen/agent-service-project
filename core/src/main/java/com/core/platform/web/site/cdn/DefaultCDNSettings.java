package com.core.platform.web.site.cdn;


import com.core.utils.StringUtils;

public class DefaultCDNSettings implements CDNSettings {
    private String[] cdnHosts;
    private boolean supportHTTPS;

    @Override
    public String[] getCDNHosts() {
        return cdnHosts;
    }

    @Override
    public boolean supportHTTPS() {
        return supportHTTPS;
    }

    public void setCDNHostsWithCommaDelimitedValue(String cdnHosts) {
        if (StringUtils.hasText(cdnHosts))
            this.cdnHosts = cdnHosts.split(",");
    }

    public void setSupportHTTPS(boolean supportHTTPS) {
        this.supportHTTPS = supportHTTPS;
    }
}
