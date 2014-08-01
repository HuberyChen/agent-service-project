package com.core.platform.web.site.session;


import com.core.collection.Key;
import com.core.utils.AssertUtils;

public class SecureSessionContext extends SessionContext {
    private boolean underSecureRequest;

    @Override
    public <T> T get(Key<T> key) {
        AssertUtils.assertTrue(underSecureRequest, "secure session can only be used under https");
        return super.get(key);
    }

    @Override
    public <T> void set(Key<T> key, T value) {
        AssertUtils.assertTrue(underSecureRequest, "secure session can only be used under https");
        super.set(key, value);
    }

    void underSecureRequest() {
        underSecureRequest = true;
    }
}
