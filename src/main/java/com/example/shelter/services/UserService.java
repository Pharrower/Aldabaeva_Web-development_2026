package com.example.shelter.services;

import com.example.shelter.entities.User;
import com.example.shelter.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void registerUser(User user) {
        // Шифруем пароль перед сохранением
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        // Всем новым пользователям по умолчанию даем роль обычного юзера
        user.setRole("ROLE_USER");
        userRepository.save(user);
    }
}