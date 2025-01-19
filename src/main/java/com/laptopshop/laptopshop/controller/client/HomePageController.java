package com.laptopshop.laptopshop.controller.client;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.laptopshop.laptopshop.domain.dto.RegisterDTO;
import com.laptopshop.laptopshop.service.ProductService;
import com.laptopshop.laptopshop.service.UserService;

import jakarta.validation.Valid;

@Controller
public class HomePageController {
    private final ProductService productService;
    private final UserService userService;

    public HomePageController(ProductService productService, UserService userService) {
        this.productService = productService;
        this.userService = userService;
    }

    @GetMapping("/")
    public ModelAndView getHomePage() {
        return new ModelAndView("client/homepage/show", "products", this.productService.getAllProduct());
    }

    @GetMapping("/register")
    public ModelAndView getRegisterPage() {
        return new ModelAndView("client/auth/register", "registerUser", new RegisterDTO());
    }

    @PostMapping("/register")
    public String handleRegister(@ModelAttribute("registerUser") @Valid RegisterDTO registerUser,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "client/auth/register";
        }

        this.userService.handleRegisterUser(registerUser);

        return "redirect:/login";
    }

    @GetMapping("/login")
    public String getLoginPage(Model model) {

        model.addAttribute("image", this.userService.getUserByEmail("user@gmail.com"));
        return "client/auth/login";
    }

    @GetMapping("/access-deny")
    public String getDenyPage() {
        return "client/homepage/404";
    }

}
