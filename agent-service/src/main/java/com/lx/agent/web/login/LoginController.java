package com.lx.agent.web.login;

import com.lx.agent.api.LoginAPIService;
import com.lx.agent.web.AgentSiteController;
import org.springframework.stereotype.Controller;

import java.util.Map;

/**
 * @author hubery.chen
 */
@Controller
public class LoginController extends AgentSiteController implements LoginAPIService {

    @Override
    public String login(Map<String, Object> model) {
        return "login/login";
    }

    @Override
    public String register(Map<String, Object> model) {
        return "login/register";
    }

}
