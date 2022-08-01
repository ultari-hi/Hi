package com.hi.controller;

import com.hi.domain.Payment;
import com.hi.dto.PaymentReqDto;
import com.hi.dto.ReservationDto;
import com.hi.service.PaymentService;
import com.hi.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    //결제,예약 생성
    @PostMapping("/payment/new")
    public Long newPayment(@RequestBody PaymentReqDto dto){
        return paymentService.newPayment(dto);
    }

    //결제,예약 결과에 따른 상태 수정
    @PatchMapping("/payment/result")
    public String paymentResult(@RequestBody PaymentReqDto dto){
        paymentService.paymentResult(dto);
        return "success";
    }
}
