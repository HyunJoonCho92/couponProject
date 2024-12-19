package com.example.coupon_api.controller;

import com.example.coupon_api.dto.UserDto;
import com.example.coupon_api.entity.User;
import com.example.coupon_api.service.UserService;
import com.example.coupon_api.vo.UserVo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/signup") // register 없어도 됨
    // validation 생각하기
    public UserVo signUp(@RequestBody UserDto userDto) {
        return userService.signUp(userDto.getUsername(), userDto.getPassword(), userDto.getEmail());
    }

}
