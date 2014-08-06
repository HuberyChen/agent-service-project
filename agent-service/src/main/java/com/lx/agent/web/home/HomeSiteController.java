package com.lx.agent.web.home;

import com.lx.agent.api.HomeAPIService;
import com.lx.agent.inteceptor.LoginRequired;
import com.lx.agent.web.AgentSiteController;

import java.util.Map;

/**
 * @author hubery.chen
 */
@LoginRequired
public class HomeSiteController extends AgentSiteController implements HomeAPIService {

    @Override
    public String home(Map<String, Object> model) {
        return "home";
    }
}
