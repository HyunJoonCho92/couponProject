package com.example.coupon_api.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class CouponPolicy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name; // 쿠폰 이름

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CouponType type; // 쿠폰 타입(일반 쿠폰, 선착순 쿠폰)

    @Column(nullable = false)
    private LocalDateTime validUntil; // 유효시간

    @Column(nullable = false)
    private int totalAmount; // 총량

    private int issuedAmount; // 발급된 수량

    @Version
    private Long version;

    private String discount; // 할인금액

    private int minUsageAmount; // 최소사용금액

    private LocalDateTime issuanceStart; // 발급시작일시

    private LocalDateTime issuanceEnd; // 발급종료일시

    private LocalDateTime createdAt; // 생성일시

    private LocalDateTime updatedAt; // 수정일시

    private String updatedBy; // 수정자
    
}
