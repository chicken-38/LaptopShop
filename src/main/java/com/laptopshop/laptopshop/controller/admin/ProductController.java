package com.laptopshop.laptopshop.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.laptopshop.laptopshop.domain.Product;
import com.laptopshop.laptopshop.service.ProductService;

import jakarta.validation.Valid;

@Controller
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/admin/product")
    public String getProduct(Model model) {
        model.addAttribute("products", this.productService.getAllProduct());
        return "admin/product/show";
    }

    @GetMapping("/admin/product/create")
    public String getCreateProductPage(Model model) {
        model.addAttribute("newProduct", new Product());
        model.addAttribute("factories", this.productService.getAllFactory());
        model.addAttribute("categories", this.productService.getAllCategory());
        return "admin/product/create";
    }

    @PostMapping("/admin/product/create")
    public String createProduct(Model model,
            @ModelAttribute("newProduct") @Valid Product newProduct,
            BindingResult bindingResult,
            @RequestParam("productImage") MultipartFile file,
            RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("factories", this.productService.getAllFactory());
            model.addAttribute("categories", this.productService.getAllCategory());
            return "admin/product/create";
        }
        this.productService.createProduct(newProduct, file);
        redirectAttributes.addFlashAttribute("message", "Successfully created product!");
        return "redirect:/admin/product";
    }

    @GetMapping("/admin/product/update/{id}")
    public String getUpdateProductPage(Model model, @PathVariable long id) {
        try {
            model.addAttribute("product", this.productService.getProductById(id));
            model.addAttribute("factories", this.productService.getAllFactory());
            model.addAttribute("categories", this.productService.getAllCategory());
        } catch (Exception exception) {
            System.out.println(">>>> exception: " + exception.getMessage());
            model.addAttribute("message", "Product not found!");
        }
        return "admin/product/update";
    }

    @PostMapping("/admin/product/update")
    public String updateProduct(Model model,
            @ModelAttribute("product") @Valid Product product,
            BindingResult bindingResult,
            @RequestParam("productImage") MultipartFile file,
            RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("factories", this.productService.getAllFactory());
            model.addAttribute("categories", this.productService.getAllCategory());
            return "admin/product/update";
        }
        try {
            redirectAttributes.addFlashAttribute("message", this.productService.updateProduct(product, file));
        } catch (Exception exception) {
            System.out.println(">>>> exception: " + exception.getMessage());
            redirectAttributes.addFlashAttribute("message", "Failed to update product");
        }
        return "redirect:/admin/product";
    }

    @GetMapping("/admin/product/delete/{id}")
    public String getDeleteProductPage(Model model, @PathVariable long id) {
        try {
            model.addAttribute("product", this.productService.getProductById(id));
        } catch (Exception exception) {
            System.out.println(">>>> exception: " + exception.getMessage());
            model.addAttribute("message", "Product not found!");
        }
        return "admin/product/delete";
    }

    @PostMapping("/admin/product/delete")
    public String deleteProduct(@ModelAttribute Product product, RedirectAttributes redirectAttributes) {
        try {
            this.productService.deleteProductById(product.getId());
            redirectAttributes.addFlashAttribute("message", "Successfully deleted product!");
        } catch (Exception exception) {
            System.out.println(">>>> exception: " + exception.getMessage());
            redirectAttributes.addFlashAttribute("message", "Failed to delete product!");
        }
        return "redirect:/admin/product";
    }

    @GetMapping("/admin/product/{id}")
    public String getDetailProductPageByAdmin(@PathVariable long id, Model model) {
        try {
            model.addAttribute("product", this.productService.getProductById(id));
        } catch (Exception exception) {
            System.out.println(">>>> exception: " + exception.getMessage());
            model.addAttribute("message", "Product not found!");
        }
        return "admin/product/detail";
    }

}
