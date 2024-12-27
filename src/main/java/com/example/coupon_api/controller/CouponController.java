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

    // 실시간 쿠폰 요청
    // 실시간requestCoupon 메소드

    // 일반 쿠폰 요청

    @PostMapping("/request")
    public ResponseEntity<CouponResponseDto> requestCoupon(@RequestBody CouponRequestDto requestDTO) {
        // 실시간 인지, 일반 쿠폰인지 조건 확인

        CouponResponseDto responseDTO = couponService.requestCoupon(requestDTO);
        return ResponseEntity.ok(responseDTO);

    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<CouponDetailsVo>> getCouponsByUserId(@PathVariable Long userId) {
        List<CouponDetailsVo> coupons = couponService.getCouponsByUserId(userId);
        return ResponseEntity.ok(coupons);
    }
}
