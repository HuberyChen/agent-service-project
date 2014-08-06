package com.lx.agent.request;

import com.lx.agent.view.UserView;

/**
 * @author hubery.chen
 */
public class RegisterRequest {

    private UserView user;

    public UserView getUser() {
        return user;
    }

    public void setUser(UserView user) {
        this.user = user;
    }
}