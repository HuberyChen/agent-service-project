package com.hubery.agent;

import com.quidsi.core.platform.monitor.Track;
import com.quidsi.core.platform.web.rest.security.Protected;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

/**
 * @author neo
 */
public interface agent-service-projectAPIService {

    @RequestMapping(value = "/execute", method = RequestMethod.POST)
    @Protected(system = "system", operation = "operation")
    @ResponseBody
    @Track(warningThresholdInMs = 5000)
    agent-service-projectResponse execute(@Valid @RequestBody agent-service-projectRequest request);
}
