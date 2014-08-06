package com.lx.agent.web.business;

import com.core.http.HTTPConstants;
import com.lx.agent.api.BusinessRestAPIService;
import com.lx.agent.request.UploadDetailRequest;
import com.lx.agent.service.file.FileManager;
import com.lx.agent.web.AgentRestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
public class BusinessRestController extends AgentRestController implements BusinessRestAPIService {

    private final Logger logger = LoggerFactory.getLogger(BusinessRestController.class);

    private FileManager fileManager;

    @Override
    @ResponseBody
    public ResponseEntity<Map<String, Object>> uploadDetail(@Valid @RequestBody UploadDetailRequest request) {
        Map<String, Object> jsonMap = new HashMap<>();

        StringBuilder msg = new StringBuilder();
        if (!request.getImage().isEmpty()) {
            try {
                fileManager.saveFile(request.getImage());
            } catch (Exception e) {
                msg.append("We are sorry that upload progress has encountered an error, please try again!");
                logger.error(e.getMessage(), e);
            }
        } else {
            msg.append("Upload Failed : Please choose a file!");
        }

        jsonMap.put("msg", msg.toString());

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("Content-Type", HTTPConstants.CONTENT_TYPE_HTML);
        return new ResponseEntity<>(jsonMap, responseHeaders, HttpStatus.CREATED);
    }

    @Inject
    public void setFileManager(FileManager fileManager) {
        this.fileManager = fileManager;
    }
}
