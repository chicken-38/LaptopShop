package com.laptopshop.laptopshop.controller.client;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.laptopshop.laptopshop.service.ProductService;

@Controller
public class HomePageController {
    private final ProductService productService;

    public HomePageController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/")
    public String getHomePage(Model model) {
        model.addAttribute("products", this.productService.getAllProduct());
        return "client/homepage/show";
    }

}
