package com.hubery.agent;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

public interface AgentAPIService {

    @RequestMapping(value = "/execute", method = RequestMethod.POST)
    @ResponseBody
    AgentResponse execute(@Valid @RequestBody AgentRequest request);
}
