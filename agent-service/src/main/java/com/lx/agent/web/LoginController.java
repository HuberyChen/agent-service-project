package com.lx.agent.web;

import com.core.platform.web.site.cookie.RequireCookie;
import com.core.platform.web.site.session.RequireSession;
import com.lx.agent.api.LoginAPIService;
import com.lx.agent.domain.User;
import com.lx.agent.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import java.util.Map;

/**
 * @author hubery.chen
 */
//@HTTPSOnly
@Controller
@RequireCookie
@RequireSession
public class LoginController extends AgentSiteController implements LoginAPIService {

    private UserService userService;

    @Override
    public String login(Map<String, Object> model) {
        return "/login";
    }

    @Override
    @ResponseBody
    public String loginConfirm(@RequestParam("emailAddress") String emailAddress, @RequestParam("password") String password, Map<String, Object> model) {
        User user = userService.get(emailAddress, password);
        if (null == user) {
            model.put("msgType", "error");
            return "login";
        }
//        login(user);
        model.put("msgType", "success");
        return "home";
    }

    @Override
    public String register(Map<String, Object> model) {
        return "register";
    }

    @Inject
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
