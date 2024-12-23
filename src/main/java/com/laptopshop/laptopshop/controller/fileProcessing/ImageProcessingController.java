package com.laptopshop.laptopshop.controller.fileProcessing;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.laptopshop.laptopshop.service.ImageStorageService;

@RestController
public class ImageProcessingController {
    private final ImageStorageService imageStorageService;

    public ImageProcessingController(ImageStorageService imageStorageService) {
        this.imageStorageService = imageStorageService;
    }

    @GetMapping("/images/{fileName}")
    public ResponseEntity<byte[]> readDetailFile(@PathVariable String fileName) {
        try {
            byte[] bytes = this.imageStorageService.readFileContent(fileName);
            return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(bytes);
        } catch (Exception exception) {
            return ResponseEntity.noContent().build();
        }

    }
}
