package com.example.coupon_api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class CouponResponseDto {
    private String couponCode;
    private LocalDateTime issuedDate;
}
