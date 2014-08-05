package com.lx.agent.api;

import com.lx.agent.request.LoginRequest;
import com.lx.agent.request.RegisterRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

/**
 * @author hubery.chen
 */
public interface LoginRestAPIService {

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    Map<String, Object> loginConfirm(LoginRequest request);

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    Map<String, Object> register(RegisterRequest request);
}
