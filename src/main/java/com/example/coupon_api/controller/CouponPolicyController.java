package com.example.coupon_api.controller;

import com.example.coupon_api.entity.CouponPolicy;
import com.example.coupon_api.service.CouponPolicyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/coupons")
@RequiredArgsConstructor
public class CouponPolicyController {

    private final CouponPolicyService couponPolicyService;

    @PostMapping
    public ResponseEntity<CouponPolicy> createPolicy(@RequestBody CouponPolicy policy) {
        return ResponseEntity.ok(couponPolicyService.createPolicy(policy));
    }

    @GetMapping
    public ResponseEntity<List<CouponPolicy>> getAllPolicies() {
        return ResponseEntity.ok(couponPolicyService.getAllPolicies());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CouponPolicy> getPolicyById(@PathVariable Long id) {
        return couponPolicyService.getPolicyById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePolicy(@PathVariable Long id) {
        couponPolicyService.deletePolicy(id);
        return ResponseEntity.noContent().build();
    }
}
