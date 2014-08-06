package com.lx.agent.service.file;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author hubery.chen
 */
public interface FileManager {

    String saveFile(MultipartFile file) throws Exception;
}
