package com.example.coupon_api.controller;

import com.example.coupon_api.dto.CouponRequestDto;
import com.example.coupon_api.dto.CouponResponseDto;
import com.example.coupon_api.service.CouponService;
import com.example.coupon_api.vo.CouponDetailsVo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/coupons")
@RequiredArgsConstructor
public class CouponController {
    private final CouponService couponService;

    @PostMapping("/request")
    public ResponseEntity<CouponResponseDto> requestCoupon(@RequestBody CouponRequestDto requestDTO) {
        CouponResponseDto responseDTO = couponService.requestCoupon(requestDTO);
        return ResponseEntity.ok(responseDTO);

    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<CouponDetailsVo>> getCouponsByUserId(@PathVariable Long userId) {
        List<CouponDetailsVo> coupons = couponService.getCouponsByUserId(userId);
        return ResponseEntity.ok(coupons);
    }
}
