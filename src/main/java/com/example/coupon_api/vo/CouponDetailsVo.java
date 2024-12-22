package com.example.coupon_api.vo;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
public class CouponDetailsVo {
    private final String couponCode;
    private final LocalDateTime issuedDate;
}
