package com.laptopshop.laptopshop.controller.admin;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
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

import com.laptopshop.laptopshop.domain.User;
import com.laptopshop.laptopshop.helper.ImageProcessing;
import com.laptopshop.laptopshop.service.ImageStorageService;
import com.laptopshop.laptopshop.service.UserService;

import jakarta.validation.Valid;

@Controller
public class UserController {

    private final UserService userService;
    private final ImageStorageService imageStorageService;
    private final PasswordEncoder passwordEncoder;
    private final ImageProcessing imageProcessing;

    public UserController(UserService userService, ImageStorageService imageStorageService,
            PasswordEncoder passwordEncoder, ImageProcessing imageProcessing) {
        this.userService = userService;
        this.imageStorageService = imageStorageService;
        this.passwordEncoder = passwordEncoder;
        this.imageProcessing = imageProcessing;
    }

    @PostMapping("/admin/user/delete")
    public String deleteUser(@ModelAttribute User user, RedirectAttributes redirectAttributes) {
        this.userService.getUserById(user.getId()).ifPresentOrElse(currentUser -> {
            try {
                this.imageStorageService.deleteFileByName(currentUser.getAvatar());
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            this.userService.deleteUserById(currentUser.getId());
            redirectAttributes.addFlashAttribute("message", "Successfully deleted user!");
        }, () -> redirectAttributes.addFlashAttribute("message", "Failed to delete user!"));
        return "redirect:/admin/user";
    }

    @GetMapping("/admin/user/delete/{id}")
    public String getDeleteUserPage(Model model, @PathVariable() long id) {
        this.userService.getUserById(id).ifPresentOrElse(user -> model.addAttribute("user", user),
                () -> model.addAttribute("message", "User not found!"));
        return "admin/user/delete";
    }

    @PostMapping("/admin/user/update")
    public String updateUser(Model model, @ModelAttribute("user") @Valid User user, BindingResult bindingResult,
            @RequestParam("avatarFile") MultipartFile file,
            RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("roles", this.userService.getAllRole());
            return "admin/user/update";
        }
        this.userService.getUserById(user.getId()).ifPresentOrElse(currentUser -> {
            currentUser.setFullName(user.getFullName());
            currentUser.setAddress(user.getAddress());
            currentUser.setPhone(user.getPhone());
            try {
                String currentAvatar = currentUser.getAvatar();
                currentUser.setAvatar(this.imageStorageService.storeFile(file));
                this.imageStorageService.deleteFileByName(currentAvatar);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            this.userService.handleSaveUser(currentUser);
            redirectAttributes.addFlashAttribute("message", "Successfully updated user!");
        }, () -> redirectAttributes.addFlashAttribute("message", "User update failed!"));
        return "redirect:/admin/user";
    }

    @GetMapping("/admin/user/update/{id}")
    public String getUpdateUserPage(Model model, @PathVariable long id) {
        this.userService.getUserById(id).ifPresentOrElse(currentUser -> {
            currentUser.setAvatar(this.imageProcessing.getUrl(currentUser.getAvatar()));
            model.addAttribute("user", currentUser);
            model.addAttribute("roles", this.userService.getAllRole());
        },
                () -> model.addAttribute("message", "User not found!"));
        return "admin/user/update";
    }

    @GetMapping("/admin/user/{id}")
    public String getUserDetailsPage(@PathVariable long id, Model model) {
        this.userService.getUserById(id).ifPresentOrElse(currentUser -> {
            currentUser.setAvatar(this.imageProcessing.getUrl(currentUser.getAvatar()));
            model.addAttribute("user", currentUser);
        },
                () -> model.addAttribute("message", "User not found!"));
        return "admin/user/detail";
    }

    @GetMapping("/admin/user/create")
    public String getCreateUserPage(Model model) {
        model.addAttribute("newUser", new User());
        model.addAttribute("roles", this.userService.getAllRole());
        return "admin/user/create";
    }

    @PostMapping("/admin/user/create")
    public String createUser(Model model, @ModelAttribute("newUser") @Valid User newUser, BindingResult bindingResult,
            @RequestParam("avatarFile") MultipartFile file,
            RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("roles", this.userService.getAllRole());
            return "admin/user/create";
        }
        try {
            newUser.setAvatar(this.imageStorageService.storeFile(file));
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        newUser.setPassword(this.passwordEncoder.encode(newUser.getPassword()));
        this.userService.handleSaveUser(newUser);
        redirectAttributes.addFlashAttribute("message", "Successfully created user!");
        return "redirect:/admin/user";
    }

    @GetMapping("/admin/user")
    public String getUserPage(Model model) {
        List<User> users = this.userService.getAllUsers();
        model.addAttribute("users", users);
        return "admin/user/show";
    }

}
