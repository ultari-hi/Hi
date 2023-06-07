package com.hi.controller;

import com.hi.config.auth.CustomUserDetails;
import com.hi.dto.IamPortWebhookDto;
import com.hi.dto.PaymentReqDto;
import com.hi.dto.PaymentResDto;
import com.hi.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/payment")
public class PaymentController {

    private final PaymentService paymentService;

    //결제,예약 생성
    @PostMapping("/new")
    public PaymentResDto createPayment(@AuthenticationPrincipal CustomUserDetails user , @RequestBody PaymentReqDto dto){
        return paymentService.createPayment(user.getUser(), dto);
    }

    //webhook 으로 검증
    @PostMapping("/result")
    public ResponseEntity<?> verifyPayment(@RequestBody IamPortWebhookDto dto) {
        return ResponseEntity.ok(paymentService.verifyPayment(dto));
    }
}
