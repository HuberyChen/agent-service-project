package com.lx.agent.service.file;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author hubery.chen
 */
public class LocalFileManager implements FileManager {

    private final Logger logger = LoggerFactory.getLogger(LocalFileManager.class);
    private final String uploadDir;

    public LocalFileManager(String uploadDir) {
        this.uploadDir = uploadDir;
    }

    @Override
    public String saveFile(MultipartFile file) throws Exception {
        File dir = new File(uploadDir);
        dir.mkdirs();
        String fileName = System.currentTimeMillis() + "-" + file.getOriginalFilename();
        String uploadFileDir = uploadDir + '/' + fileName;
        try (InputStream inputStream = file.getInputStream();
             FileOutputStream fileOutputStream = new FileOutputStream(uploadFileDir)) {
            FileCopyUtils.copy(inputStream, fileOutputStream);
        } catch (IOException e) {
            logger.error(e.getMessage());
            throw e;
        }
        logger.info("save file on local disk, originalFileName:{}, uploadFileDir:{}", file.getOriginalFilename(), uploadFileDir);
        return fileName;
    }
}
