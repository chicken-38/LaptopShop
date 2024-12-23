package com.laptopshop.laptopshop.helper;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import com.laptopshop.laptopshop.controller.fileProcessing.ImageProcessingController;

@Component
public class ImageProcessing {
    public String getUrl(String fileName) {
        if (fileName == null || fileName.length() == 0) {
            fileName = "chicken_logo.png";
        }
        return MvcUriComponentsBuilder.fromMethodName(ImageProcessingController.class, "readDetailFile", fileName)
                .build()
                .toUri().toString();
    }
}
