package com.hubery.agent.web;

import com.hubery.agent.agent-service-projectAPIService;
import com.hubery.agent.agent-service-projectRequest;
import com.hubery.agent.agent-service-projectResponse;
import org.springframework.stereotype.Controller;

/**
 * @author neo
 */
@Controller
public class agent-service-projectServiceController implements agent-service-projectAPIService {
    // TODO: rename the method/request/response/system/operation to match the
    // business
    @Override
    public agent-service-projectResponse execute(agent-service-projectRequest request) {
        return new agent-service-projectResponse();
    }
}
