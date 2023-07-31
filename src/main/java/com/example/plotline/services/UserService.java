package com.example.plotline.services;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.plotline.entity.User;
import com.example.plotline.repository.UserRepository;
//import com.example.plotline.configuration.SecurityConfig;

@Service
public class UserService {
    private final UserRepository userRepository;
    //private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        //this.passwordEncoder = passwordEncoder;
    }

    public void registerUser(String username, String password) {
        User user = new User();
        user.setUsername(username);
        //user.setPassword(passwordEncoder.encode(password));
        user.setPassword(password);
        userRepository.save(user);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    
    public void createUser(String username, String password) {
        User user = new User();
        user.setUsername(username);
        //user.setPassword(passwordEncoder.encode(password)); 
        user.setPassword(password); 
        userRepository.save(user);
    }
}
