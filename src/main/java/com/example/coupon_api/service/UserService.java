package com.example.coupon_api.service;

import com.example.coupon_api.entity.User;
import com.example.coupon_api.repository.UserRepository;
import com.example.coupon_api.vo.UserVo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserVo signUp(String username, String password, String email) {
        // 유저가 이미 있으면 exception 구현

        String encodedPassword = passwordEncoder.encode(password);
        User user = new User();
        user.setUsername(username);
        user.setPassword(encodedPassword);
        user.setEmail(email);
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());

        userRepository.save(user);

        UserVo userVo = new UserVo();
        userVo.setUsername(username);
        userVo.setEmail(email);

        return userVo;
    }

}
