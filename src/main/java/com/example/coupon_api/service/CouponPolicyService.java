package com.example.coupon_api.service;

import com.example.coupon_api.entity.CouponPolicy;
import com.example.coupon_api.repository.CouponPolicyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CouponPolicyService {

    private final CouponPolicyRepository couponPolicyRepository;

    public CouponPolicy createPolicy(CouponPolicy policy) {
        policy.setCreatedAt(java.time.LocalDateTime.now());
        return couponPolicyRepository.save(policy);
    }

    public List<CouponPolicy> getAllPolicies() {
        return couponPolicyRepository.findAll();
    }

    public Optional<CouponPolicy> getPolicyById(Long id) {
        return couponPolicyRepository.findById(id);
    }

    public void deletePolicy(Long id) {
        couponPolicyRepository.deleteById(id);
    }
}
