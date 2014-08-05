package com.lx.agent.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

/**
 * @author hubery.chen
 */
public interface LoginAPIService {

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    String login(Map<String, Object> model);

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    String register(Map<String, Object> model);

}
