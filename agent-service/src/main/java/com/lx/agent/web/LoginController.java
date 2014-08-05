package com.lx.agent.web;

import com.lx.agent.api.LoginAPIService;
import com.lx.agent.service.UserService;
import org.springframework.stereotype.Controller;

import javax.inject.Inject;
import java.util.Map;

/**
 * @author hubery.chen
 */
//@HTTPSOnly
@Controller
public class LoginController extends AgentSiteController implements LoginAPIService {

    private UserService userService;

    @Override
    public String login(Map<String, Object> model) {
        return "login/login";
    }

    @Override
    public String register(Map<String, Object> model) {
        return "login/register";
    }

    @Inject
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
