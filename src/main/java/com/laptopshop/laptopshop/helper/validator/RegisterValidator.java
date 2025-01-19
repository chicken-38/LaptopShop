package com.laptopshop.laptopshop.helper.validator;

import com.laptopshop.laptopshop.domain.dto.RegisterDTO;
import com.laptopshop.laptopshop.service.UserService;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class RegisterValidator implements ConstraintValidator<RegisterChecked, RegisterDTO> {

    private final UserService userService;

    public RegisterValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean isValid(RegisterDTO user, ConstraintValidatorContext context) {

        boolean valid = true;

        // check if firstName fields blank
        if (user.getFirstName() == null || user.getFirstName().trim().length() == 0) {
            context.buildConstraintViolationWithTemplate("First name must not be empty.")
                    .addPropertyNode("firstName")
                    .addConstraintViolation()
                    .disableDefaultConstraintViolation();
            valid = false;
        }

        // check if lastName fields blank
        if (user.getLastName() == null || user.getLastName().trim().length() == 0) {
            context.buildConstraintViolationWithTemplate("Last name must not be empty.")
                    .addPropertyNode("lastName")
                    .addConstraintViolation()
                    .disableDefaultConstraintViolation();
            valid = false;
        }

        // check email
        if (!user.getEmail().matches("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")) {
            context.buildConstraintViolationWithTemplate("The email is not valid.")
                    .addPropertyNode("email")
                    .addConstraintViolation()
                    .disableDefaultConstraintViolation();
            valid = false;
        }

        if (this.userService.isEmailExist(user.getEmail())) {
            context.buildConstraintViolationWithTemplate("The email is exist.")
                    .addPropertyNode("email")
                    .addConstraintViolation()
                    .disableDefaultConstraintViolation();
            valid = false;
        }

        // check strong password
        // if
        // (!user.getPassword().matches("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!*()]).{8,}$"))
        // {
        // context.buildConstraintViolationWithTemplate(
        // "Must be 8 characters long and combination of uppercase letters, lowercase
        // letters, numbers, special characters.")
        // .addPropertyNode("password")
        // .addConstraintViolation()
        // .disableDefaultConstraintViolation();
        // valid = false;
        // }

        // Check if password fields match
        if (!user.getPassword().equals(user.getConfirmPassword())) {
            context.buildConstraintViolationWithTemplate("Passwords must match.")
                    .addPropertyNode("confirmPassword")
                    .addConstraintViolation()
                    .disableDefaultConstraintViolation();
            valid = false;
        }

        // Additional validations can be added here

        return valid;
    }
}
