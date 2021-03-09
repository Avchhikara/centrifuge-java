package com.ems.centrifuge.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

@Service
public class StorageService {

    private final String uploadDir;

    public StorageService() {
        uploadDir = Paths.get(".").toAbsolutePath().normalize().toString() + "/uploads/";
        createUploadDir();
    }

    private void createUploadDir() {
        File dir = new File(uploadDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    public String saveFile(MultipartFile file) throws IOException {
        String newFileName = java.util.UUID.randomUUID().toString() + ".jpg";
        File newFile = new File(uploadDir + "/" + newFileName);
        file.transferTo(newFile);
        return newFileName;
    }

    public File getFile(String fileName) {
        return new File(uploadDir + "/" + fileName);
    }
}
