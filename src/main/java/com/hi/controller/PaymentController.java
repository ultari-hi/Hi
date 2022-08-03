package com.hi.controller;

import com.hi.dto.PaymentReqDto;
import com.hi.dto.PaymentResDto;
import com.hi.service.PaymentService;
import com.hi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;
    private final UserService userService;

    //결제,예약 생성
    @PostMapping("/payment/new")
    public Long newPayment(@RequestBody PaymentReqDto dto){
        return paymentService.newPayment(dto);
    }

    //결제,예약 정보 입력 페이지
    @GetMapping("/payment")
    public PaymentResDto paymentPage(@RequestParam Long userId){
        return new PaymentResDto(userService.findUser(userId));
    }

    //결제,예약 결과에 따른 상태 수정
    @PatchMapping("/payment/result")
    public String paymentResult(@RequestBody PaymentReqDto dto){
        paymentService.paymentResult(dto);
        return "success";
    }
}
