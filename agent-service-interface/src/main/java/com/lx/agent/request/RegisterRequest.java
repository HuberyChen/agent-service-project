package com.lx.agent.request;

import com.lx.agent.view.UserView;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author hubery.chen
 */
@XmlRootElement(name = "register-request")
@XmlAccessorType(XmlAccessType.FIELD)
public class RegisterRequest {

    @XmlElement(name = "user")
    @NotNull
    private UserView userView;

    public UserView getUserView() {
        return userView;
    }

    public void setUserView(UserView userView) {
        this.userView = userView;
    }
}
