package com.hubery.agent.web;

import com.hubery.agent.AgentAPIService;
import com.hubery.agent.AgentRequest;
import com.hubery.agent.AgentResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@Controller
public class AgentController implements AgentAPIService {

    @Override
    public AgentResponse execute(@Valid @RequestBody AgentRequest request) {
        return null;
    }
}
