package com.laptopshop.laptopshop.service;

import java.util.List;
import java.util.Optional;

import com.laptopshop.laptopshop.domain.Role;
import com.laptopshop.laptopshop.domain.User;

public interface UserService {
    public void handleSaveUser(User user);

    public List<User> getAllUsers();

    public List<User> getAllUsersByEmail(String email);

    public Optional<User> getUserById(long id);

    public void deleteUserById(long id);

    public List<Role> getAllRole();
}
