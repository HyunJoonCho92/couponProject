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

        // 일반 쿠폰 발급 조건 추가
        if ("GENERAL".equals(policy.getType())) {
            // 특정 기간 동안 1개씩만 발급
            LocalDateTime now = LocalDateTime.now();
            if (couponRepository.existsByUserIdAndCouponPolicyIdAndIssuedDateBetween(
                    requestDTO.getUserId(), requestDTO.getCouponPolicyId(),
                    now.withDayOfMonth(1), now.withDayOfMonth(now.toLocalDate().lengthOfMonth()))) {
                throw new CouponException(CouponErrorCode.COUPON_ALREADY_ISSUED_THIS_PERIOD);
            }
        }

        String couponCode = UUID.randomUUID().toString(); // 짧게 만들 수 있는 방법있을까??
        Coupon coupon = new Coupon();
        coupon.setCouponCode(couponCode);
        coupon.setUserId(requestDTO.getUserId());
        coupon.setCouponPolicy(policy);
        coupon.setIssuedDate(LocalDateTime.now());
        coupon.setType(policy.getType());
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
