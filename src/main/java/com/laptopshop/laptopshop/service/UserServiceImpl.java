package com.laptopshop.laptopshop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.laptopshop.laptopshop.domain.Role;
import com.laptopshop.laptopshop.domain.User;
import com.laptopshop.laptopshop.repository.RoleRepository;
import com.laptopshop.laptopshop.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public void handleSaveUser(User user) {
        this.userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return this.userRepository.findAll();
    }

    @Override
    public List<User> getAllUsersByEmail(String email) {
        return this.userRepository.findByEmail(email);
    }

    @Override
    public Optional<User> getUserById(long id) {
        return this.userRepository.findById(id);
    }

    @Override
    public void deleteUserById(long id) {
        this.userRepository.deleteById(id);
    }

    @Override
    public List<Role> getAllRole() {
        return this.roleRepository.findAll();
    }
}
