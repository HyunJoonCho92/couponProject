package com.example.coupon_api.controller;

import com.example.coupon_api.entity.CouponPolicy;
import com.example.coupon_api.entity.CouponType;
import com.example.coupon_api.service.CouponPolicyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CouponPolicyController.class)
class CouponPolicyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CouponPolicyService couponPolicyService;

    private CouponPolicy couponPolicy;

    @BeforeEach // 각 메소드 실행 전 객체 초기화 해줌
    void setup() {
        couponPolicy = new CouponPolicy();
        couponPolicy.setId(1L);
        couponPolicy.setName("Discount Coupon");
        couponPolicy.setType(CouponType.REGULAR);
        couponPolicy.setValidUntil(LocalDateTime.now().plusMonths(1));
        couponPolicy.setTotalAmount(1000);
        couponPolicy.setIssuedAmount(100);
        couponPolicy.setDiscount("10%");
        couponPolicy.setMinUsageAmount(100);
        couponPolicy.setIssuanceStart(LocalDateTime.now());
        couponPolicy.setIssuanceEnd(LocalDateTime.now().plusDays(30));
        couponPolicy.setCreatedAt(LocalDateTime.now());
        couponPolicy.setUpdatedAt(LocalDateTime.now());
        couponPolicy.setUpdatedBy("admin");
    }

    @Test
    void createPolicy() throws Exception {
        when(couponPolicyService.createPolicy(any(CouponPolicy.class))).thenReturn(couponPolicy);

        mockMvc.perform(post("/api/coupons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Discount Coupon\"," +
                                "\"type\":\"REGULAR\"," +
                                "\"validUntil\":\"2024-12-31T23:59:59\"," +
                                "\"totalAmount\":1000," +
                                "\"issuedAmount\":100," +
                                "\"discount\":\"10%\"," +
                                "\"minUsageAmount\":100," +
                                "\"issuanceStart\":\"2024-12-01T00:00:00\"," +
                                "\"issuanceEnd\":\"2024-12-30T23:59:59\"," +
                                "\"createdAt\":\"2024-12-01T12:00:00\"," +
                                "\"updatedAt\":\"2024-12-01T12:00:00\"," +
                                "\"updatedBy\":\"admin\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Discount Coupon"))
                .andExpect(jsonPath("$.type").value("REGULAR"))
                .andExpect(jsonPath("$.totalAmount").value(1000))
                .andExpect(jsonPath("$.discount").value("10%"))
                .andExpect(jsonPath("$.minUsageAmount").value(100));
    }

    @Test
    void getAllPolicies() throws Exception {
        when(couponPolicyService.getAllPolicies()).thenReturn(Arrays.asList(couponPolicy));

        mockMvc.perform(get("/api/coupons")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("Discount Coupon"))
                .andExpect(jsonPath("$[0].type").value("REGULAR"))
                .andExpect(jsonPath("$[0].totalAmount").value(1000))
                .andExpect(jsonPath("$[0].discount").value("10%"));
    }

    @Test
    void getPolicyById() throws Exception {
        when(couponPolicyService.getPolicyById(1L)).thenReturn(Optional.of(couponPolicy));

        mockMvc.perform(get("/api/coupons/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Discount Coupon"))
                .andExpect(jsonPath("$.type").value("REGULAR"))
                .andExpect(jsonPath("$.totalAmount").value(1000))
                .andExpect(jsonPath("$.discount").value("10%"));
    }

    @Test
    void getPolicyById_NotFound() throws Exception {
        when(couponPolicyService.getPolicyById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/coupons/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void deletePolicy() throws Exception {
        Mockito.doNothing().when(couponPolicyService).deletePolicy(1L);

        mockMvc.perform(delete("/api/coupons/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}
