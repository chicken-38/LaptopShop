package com.laptopshop.laptopshop.service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.laptopshop.laptopshop.domain.Cart;
import com.laptopshop.laptopshop.domain.Role;
import com.laptopshop.laptopshop.domain.User;
import com.laptopshop.laptopshop.domain.dto.RegisterDTO;
import com.laptopshop.laptopshop.helper.ImageProcessing;
import com.laptopshop.laptopshop.repository.CartRepository;
import com.laptopshop.laptopshop.repository.RoleRepository;
import com.laptopshop.laptopshop.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final ImageStorageService imageStorageService;
    private final ImageProcessing imageProcessing;
    private final CartRepository cartRepository;

    public UserServiceImpl(UserRepository userRepository,
            RoleRepository roleRepository,
            PasswordEncoder passwordEncoder,
            ImageStorageService imageStorageService,
            ImageProcessing imageProcessing,
            CartRepository cartRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.imageStorageService = imageStorageService;
        this.imageProcessing = imageProcessing;
        this.cartRepository = cartRepository;
    }

    @Override
    public List<Role> getAllRole() {
        return this.roleRepository.findAll();
    }

    @Override
    public List<User> getAllUsers() {
        return this.userRepository.findAll();
    }

    @Override
    public User getUserById(long id) {
        User user = this.userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        user.setAvatarUrl(this.imageProcessing.getUrl(user.getAvatar()));
        return user;
    }

    @Override
    public User getUserByEmail(String email) {
        User user = this.userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found!"));
        return user;
    }

    @Override
    public User getUserToDelete(long id) {
        return this.userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found."));
    }

    @Override
    public void deleteUserById(long id) {
        User user = this.userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found."));

        try {
            this.imageStorageService.deleteFileByName(user.getAvatar());
        } catch (Exception exception) {
            System.out.println(">>>> exception: " + exception.getMessage());
        }

        this.userRepository.deleteById(id);
    }

    @Override
    public User registerDTOtoUser(RegisterDTO registerUser) {
        User user = new User();
        user.setFullName(registerUser.getFirstName().trim() + " " + registerUser.getLastName().trim());
        user.setEmail(registerUser.getEmail());
        user.setPassword(this.passwordEncoder.encode(registerUser.getPassword()));
        user.setRole(this.roleRepository.findByName("USER"));
        return user;
    }

    @Override
    public User saveUser(User user) {
        return this.userRepository.save(user);
    }

    @Override
    public void handleRegisterUser(RegisterDTO registerUser) {
        this.createCart(this.saveUser(this.registerDTOtoUser(registerUser)));
    }

    @Override
    public boolean isEmailExist(String email) {
        return this.userRepository.existsByEmail(email);
    }

    @Override
    public User updateUser(User user, MultipartFile file) {

        User currentUser = this.userRepository.findById(user.getId())
                .orElseThrow(() -> new RuntimeException("User not found."));

        currentUser.setFullName(user.getFullName());
        currentUser.setAddress(user.getAddress());
        currentUser.setPhone(user.getPhone());

        try {
            String currentAvatar = currentUser.getAvatar();
            currentUser.setAvatar(this.imageStorageService.storeFile(file));
            this.imageStorageService.deleteFileByName(currentAvatar);
        } catch (Exception exception) {
            System.out.println(">>>> exception: " + exception.getMessage());
        }

        return this.saveUser(currentUser);
    }

    @Override
    public void createUser(User newUser, MultipartFile file) {

        newUser.setPassword(this.passwordEncoder.encode(newUser.getPassword()));

        try {
            newUser.setAvatar(this.imageStorageService.storeFile(file));
        } catch (Exception exception) {
            System.out.println(">>>> exception: " + exception.getMessage());
        }

        this.createCart(this.saveUser(newUser));

    }

    @Override
    public void createCart(User user) {
        Cart cart = new Cart();
        cart.setUser(user);
        this.cartRepository.save(cart);
    }

}
