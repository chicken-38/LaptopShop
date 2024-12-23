package com.laptopshop.laptopshop.controller.client;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.laptopshop.laptopshop.service.ProductService;

@Controller
public class ItemController {
    private final ProductService productService;

    public ItemController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/product/{id}")
    public String getProductDetailPage(@PathVariable long id, Model model) {
        try {
            model.addAttribute("product", this.productService.getProductById(id));
            return "client/homepage/detail";
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            return "client/homepage/404";
        }
    }

}
