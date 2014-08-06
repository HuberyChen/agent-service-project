package com.lx.agent.api;

import com.lx.agent.request.UploadDetailRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

/**
 * @author hubery.chen
 */
public interface BusinessRestAPIService {

    @RequestMapping(value = "/business/detail", method = RequestMethod.POST)
    ResponseEntity<Map<String, Object>> uploadDetail(UploadDetailRequest request);
}
