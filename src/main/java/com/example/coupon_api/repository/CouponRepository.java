package com.example.coupon_api.repository;

import com.example.coupon_api.entity.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CouponRepository extends JpaRepository<Coupon, Long> {
    boolean existsByUserIdAndCouponPolicyId(Long userId, Long couponPolicyId);

    List<Coupon> findByUserId(Long userId);
}
