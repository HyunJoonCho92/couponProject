package com.example.coupon_api.service;

import com.example.coupon_api.dto.CouponRequestDto;
import com.example.coupon_api.dto.CouponResponseDto;
import com.example.coupon_api.entity.Coupon;
import com.example.coupon_api.entity.CouponPolicy;
import com.example.coupon_api.exception.CouponErrorCode;
import com.example.coupon_api.exception.CouponException;
import com.example.coupon_api.repository.CouponPolicyRepository;
import com.example.coupon_api.repository.CouponRepository;
import com.example.coupon_api.vo.CouponDetailsVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CouponService {
    private final CouponPolicyRepository couponPolicyRepository;
    private final CouponRepository couponRepository;

    @Transactional
    public CouponResponseDto requestCoupon(CouponRequestDto requestDTO) {
        CouponPolicy policy = couponPolicyRepository.findById(requestDTO.getCouponPolicyId())
                .orElseThrow(() -> new CouponException(CouponErrorCode.POLICY_NOT_FOUND));

        if (policy.getIssuedAmount() >= policy.getTotalAmount()) {
            throw new CouponException(CouponErrorCode.COUPONS_SOLD_OUT);
        }

        if (couponRepository.existsByUserIdAndCouponPolicyId(requestDTO.getUserId(), requestDTO.getCouponPolicyId())) {
            throw new CouponException(CouponErrorCode.COUPON_ALREADY_ISSUED);
        }

        String couponCode = UUID.randomUUID().toString();
        Coupon coupon = new Coupon();
        coupon.setCouponCode(couponCode);
        coupon.setUserId(requestDTO.getUserId());
        coupon.setCouponPolicy(policy);
        coupon.setIssuedDate(LocalDateTime.now());
        couponRepository.save(coupon);

        policy.setIssuedAmount(policy.getIssuedAmount() + 1);
        couponPolicyRepository.save(policy);

        return new CouponResponseDto(couponCode, coupon.getIssuedDate());
    }

    public List<CouponDetailsVo> getCouponsByUserId(Long userId) {
        List<Coupon> coupons = couponRepository.findByUserId(userId);
        return coupons.stream()
                .map(coupon -> new CouponDetailsVo(coupon.getCouponCode(), coupon.getIssuedDate()))
                .collect(Collectors.toList());
    }
}
