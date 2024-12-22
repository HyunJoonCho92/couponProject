package com.example.coupon_api.dto;

import lombok.Data;

@Data
public class CouponRequestDto {
    private Long userId;
    private Long couponPolicyId;
}
