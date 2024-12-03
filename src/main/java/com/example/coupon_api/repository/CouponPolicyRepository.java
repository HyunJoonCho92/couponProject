package com.example.coupon_api.repository;

import com.example.coupon_api.entity.CouponPolicy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponPolicyRepository extends JpaRepository<CouponPolicy, Long> {

}
