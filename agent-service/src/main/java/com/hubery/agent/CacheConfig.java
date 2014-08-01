package com.hubery.agent;

import com.quidsi.core.platform.DefaultCacheConfig;
import com.quidsi.core.platform.cache.CacheProvider;
import com.quidsi.core.platform.cache.CacheRegistry;
import com.quidsi.core.platform.cache.CacheSettings;
import com.quidsi.core.platform.web.rest.security.ClientAuthCacheConstants;
import com.quidsi.core.util.TimeLength;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.inject.Inject;

/**
 * @author neo
 */
@Configuration
@EnableCaching(proxyTargetClass = true)
public class CacheConfig extends DefaultCacheConfig {

    @Inject
    Environment env;

    @Override
    public CacheSettings cacheSettings() {
        CacheSettings settings = super.cacheSettings();
        settings.setCacheProvider(env.getProperty("cache.provider", CacheProvider.class, CacheProvider.EHCACHE));
        settings.setRemoteCacheServers(env.getRequiredProperty("cache.remoteCacheServers"));
        return settings;
    }

    @Override
    protected void addCaches(CacheRegistry registry) {
        registry.addCache(ClientAuthCacheConstants.CACHE_CLIENT_SECRET_KEY, TimeLength.hours(2));
        registry.addCache(ClientAuthCacheConstants.CACHE_CLIENT_PERMISSION, TimeLength.hours(2));
    }
}