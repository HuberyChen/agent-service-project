package com.lx.agent.web.login;

import com.core.crypto.EncryptionUtils;
import com.core.platform.web.site.cookie.RequireCookie;
import com.core.platform.web.site.session.RequireSession;
import com.core.utils.ClasspathResource;
import com.lx.agent.api.LoginRestAPIService;
import com.lx.agent.domain.User;
import com.lx.agent.request.LoginRequest;
import com.lx.agent.request.RegisterRequest;
import com.lx.agent.service.UserService;
import com.lx.agent.view.UserView;
import com.lx.agent.web.AgentRestController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * @author hubery.chen
 */
@Controller
@RequireCookie
@RequireSession
public class LoginRestController extends AgentRestController implements LoginRestAPIService {

    private UserService userService;

    @Override
    @ResponseBody
    public Map<String, Object> loginConfirm(@Valid @RequestBody LoginRequest request) {
        Map<String, Object> model = new HashMap<>();
        User user = userService.get(request.getEmailAddress(), request.getPassword());
        if (null == user) {
            model.put("msgType", "error");
            return model;
        }
        login(user);
        model.put("msgType", "success");
        return model;
    }

    @Override
    @ResponseBody
    public Map<String, Object> register(@Valid @RequestBody RegisterRequest request) {
        Map<String, Object> model = new HashMap<>();
        User user = toUser(request.getUser());
        Integer count = userService.count(user.getEmailAddress());
        if (null == count || count > 0) {
            model.put("msg", "该用户已存在.");
            model.put("msgType", "error");
            return model;
        }

        userService.register(user);
        if (user.getId() <= 0) {
            model.put("msg", "注册失败.");
            model.put("msgType", "error");
            return model;
        }
        model.put("msg", "注册成功.");
        model.put("msgType", "success");
        return model;
    }

    private User toUser(UserView userView) {
        User user = new User();
        user.setFirstName(userView.getFirstName());
        user.setLastName(userView.getLastName());
        user.setUserName(userView.getUserName());
        user.setPassword(EncryptionUtils.encrypt(userView.getPassword(), new ClasspathResource("public.key")));
        user.setEmailAddress(userView.getEmailAddress());
        user.setPhone(userView.getPhone());
        user.setAddress(userView.getAddress());
        user.setZipCode(userView.getZipCode());
        return user;
    }

    @Inject
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}