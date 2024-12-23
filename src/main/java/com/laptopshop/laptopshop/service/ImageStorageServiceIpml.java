package com.laptopshop.laptopshop.service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.apache.commons.io.FilenameUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImageStorageServiceIpml implements ImageStorageService {
    private final Path storageFolder = Paths.get("src/main/webapp/resources/images");

    public ImageStorageServiceIpml() {
        try {
            Files.createDirectories(storageFolder);
        } catch (IOException e) {
            throw new RuntimeException("Cannot initialize storage", e);
        }
    }

    @Override
    public String storeFile(MultipartFile file) {
        try {
            if (file.isEmpty()) {
                throw new RuntimeException("Failed to store empty file.");
            }
            String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
            String generatedFileName = UUID.randomUUID().toString().replace("-", "");
            generatedFileName = generatedFileName + "." + fileExtension;
            Path destinationFilePath = this.storageFolder.resolve(generatedFileName).normalize().toAbsolutePath();
            if (!destinationFilePath.getParent().equals(this.storageFolder.toAbsolutePath())) {
                throw new RuntimeException("Cannot store file outside current directory.");
            }
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationFilePath, StandardCopyOption.REPLACE_EXISTING);
            }
            return generatedFileName;
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file.", e);
        }
    }

    @Override
    public byte[] readFileContent(String fileName) {
        try {
            Path file = this.storageFolder.resolve(fileName);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                try (InputStream inputStream = resource.getInputStream()) {
                    return StreamUtils.copyToByteArray(inputStream);
                }
            } else {
                throw new RuntimeException("Could not read file: " + fileName);
            }
        } catch (IOException exception) {
            throw new RuntimeException("Could not read file: " + fileName, exception);
        }
    }

    @Override
    public Path load(String fileName) {
        return this.storageFolder.resolve(fileName);
    }

    @Override
    public void deleteFileByName(String fileName) {
        if (fileName != null && fileName.length() > 0) {
            try {
                FileSystemUtils.deleteRecursively(load(fileName));
            } catch (IOException exception) {
                throw new RuntimeException("Failed to delete file: " + fileName);
            }
        }
    }

}
