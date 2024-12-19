package com.example.coupon_api.controller;

import com.example.coupon_api.config.JwtConfig;
import com.example.coupon_api.dto.UserDto;
import com.example.coupon_api.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final JwtConfig jwtConfig;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/token")
    public String generateToken(@RequestBody UserDto userDto) {
        return userRepository.findByEmail(userDto.getEmail())
                .filter(user -> passwordEncoder.matches(userDto.getPassword(), user.getPassword()))
                .map(user -> jwtConfig.generateToken(user.getEmail()))
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));
    }

}
