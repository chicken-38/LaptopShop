package com.laptopshop.laptopshop.service;

import java.nio.file.Path;

import org.springframework.web.multipart.MultipartFile;

public interface ImageStorageService {
    public String storeFile(MultipartFile file);

    public byte[] readFileContent(String fileName);

    Path load(String filename);

    public void deleteFileByName(String fileName);

}
