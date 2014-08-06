package com.lx.agent.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

/**
 * @author hubery.chen
 */
public interface HomeAPIService {

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    String home(Map<String, Object> model);
}
