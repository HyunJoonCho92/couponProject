package com.example.coupon_api.exception;

public enum CouponErrorCode {
    POLICY_NOT_FOUND("Policy not found"),
    COUPONS_SOLD_OUT("Coupons are sold out"),
    COUPON_ALREADY_ISSUED("Coupon already issued to this user");

    private final String message;

    CouponErrorCode(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
