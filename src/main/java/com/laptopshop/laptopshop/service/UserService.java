package com.laptopshop.laptopshop.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.laptopshop.laptopshop.domain.Role;
import com.laptopshop.laptopshop.domain.User;
import com.laptopshop.laptopshop.domain.dto.RegisterDTO;

public interface UserService {

    public List<User> getAllUsers();

    public User getUserById(long id);

    public void deleteUserById(long id);

    public List<Role> getAllRole();

    public User registerDTOtoUser(RegisterDTO registerUser);

    public void handleRegisterUser(RegisterDTO registerUser);

    public User saveUser(User user);

    public boolean isEmailExist(String email);

    public User getUserByEmail(String email);

    public User getUserToDelete(long id);

    public User updateUser(User user, MultipartFile file);

    public void createUser(User newUser, MultipartFile file);

    public void createCart(User user);
}
