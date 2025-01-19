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
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.laptopshop.laptopshop.domain.User;
import com.laptopshop.laptopshop.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/admin/user/delete")
    public String deleteUser(@ModelAttribute User user, RedirectAttributes redirectAttributes) {

        try {
            this.userService.deleteUserById(user.getId());
            redirectAttributes.addFlashAttribute("message", "Successfully deleted user!");
        } catch (Exception exception) {
            System.out.println(">>>> exception: " + exception.getMessage());
            redirectAttributes.addFlashAttribute("message", "Failed to delete user!");
        }

        return "redirect:/admin/user";
    }

    @GetMapping("/admin/user/delete/{id}")
    public String getDeleteUserPage(Model model, @PathVariable() long id) {

        try {
            model.addAttribute("user", this.userService.getUserToDelete(id));
        } catch (Exception exception) {
            System.out.println(">>>> exception: " + exception.getMessage());
            model.addAttribute("message", "User not found!");
        }

        return "admin/user/delete";
    }

    @PostMapping("/admin/user/update")
    public String updateUser(Model model, @ModelAttribute("user") @Valid User user,
            BindingResult bindingResult,
            @RequestParam("avatarFile") MultipartFile file,
            RedirectAttributes redirectAttributes,
            HttpServletRequest request) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("roles", this.userService.getAllRole());
            return "admin/user/update";
        }
        try {
            this.userService.updateUser(user, file);
            redirectAttributes.addFlashAttribute("message", "Successfully updated user!");
        } catch (Exception exception) {
            System.out.println(">>>> exception: " + exception.getMessage());
            redirectAttributes.addFlashAttribute("message", "User update failed!");
        }

        return "redirect:/admin/user";
    }

    @GetMapping("/admin/user/update/{id}")
    public String getUpdateUserPage(Model model, @PathVariable long id) {

        try {
            model.addAttribute("user", this.userService.getUserById(id));
            model.addAttribute("roles", this.userService.getAllRole());
        } catch (Exception exception) {
            System.out.println(">>>> exception: " + exception.getMessage());
            model.addAttribute("message", "User not found!");
        }

        return "admin/user/update";
    }

    @GetMapping("/admin/user/{id}")
    public String getUserDetailsPage(@PathVariable long id, Model model) {

        try {
            model.addAttribute("user", this.userService.getUserById(id));
        } catch (Exception exception) {
            System.out.println(">>>> exception: " + exception.getMessage());
            model.addAttribute("message", "User not found!");
        }

        return "admin/user/detail";
    }

    @GetMapping("/admin/user/create")
    public String getCreateUserPage(Model model) {
        model.addAttribute("newUser", new User());
        model.addAttribute("roles", this.userService.getAllRole());
        return "admin/user/create";
    }

    @PostMapping("/admin/user/create")
    public String createUser(Model model,
            @ModelAttribute("newUser") @Valid User newUser,
            BindingResult bindingResult,
            @RequestParam("avatarFile") MultipartFile file,
            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("roles", this.userService.getAllRole());
            return "admin/user/create";
        }

        try {
            this.userService.createUser(newUser, file);
            redirectAttributes.addFlashAttribute("message", "Successfully created user!");
        } catch (Exception exception) {
            System.out.println(">>>> exception: " + exception.getMessage());
            redirectAttributes.addFlashAttribute("message", "Failed to create user!");
        }

        return "redirect:/admin/user";
    }

    @GetMapping("/admin/user")
    public ModelAndView getUserPage(Model model) {
        return new ModelAndView("admin/user/show", "users", this.userService.getAllUsers());
    }

}
