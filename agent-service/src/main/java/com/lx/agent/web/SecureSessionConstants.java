package com.lx.agent.web;


import com.core.collection.Key;

public class SecureSessionConstants {
    public static final Key<Boolean> LOGGED_IN = Key.booleanKey("loggedIn");

    public static final Key<String> API_SECURE_SESSION_ID = Key.stringKey("secureSessionId");

    public static final Key<String> LOGIN_REDIRECT_DESTINATION_URL = Key.stringKey("loginRedirectDestinationURL");

    public static final Key<String> USER_DETAILS = Key.stringKey("user");

}
