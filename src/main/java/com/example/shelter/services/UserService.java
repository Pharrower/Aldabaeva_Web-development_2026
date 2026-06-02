package com.example.shelter.services;

import com.example.shelter.entities.User;
import com.example.shelter.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // Используем конструктор (по аналогии с другими сервисами)
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));
    }

    public void updateProfile(String email, String newName, String newPassword) {
        User user = findByEmail(email);
        user.setFirstName(newName);
        if (newPassword != null && !newPassword.trim().isEmpty()) {
            user.setPassword(passwordEncoder.encode(newPassword));
        }
        userRepository.save(user);
    }

    public void registerUser(User user) {
        // Шифруем пароль перед сохранением
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        // Всем новым пользователям по умолчанию даем роль обычного юзера
        user.setRole("ROLE_USER");
        userRepository.save(user);
    }
}