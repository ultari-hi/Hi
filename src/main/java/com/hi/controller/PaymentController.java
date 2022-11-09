//package com.hi.controller;
//
//import com.hi.dto.PaymentReqDto;
//import com.hi.dto.PaymentResDto;
//import com.hi.enums.Status;
//import com.hi.service.PaymentService;
//import com.hi.service.UserService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequiredArgsConstructor
//public class PaymentController {
//
//    private final PaymentService paymentService;
//    private final UserService userService;
//
//    //결제,예약 생성
//    @PostMapping("/payment/new")
//    public String createPayment(@RequestBody PaymentReqDto dto){
//        return paymentService.createPayment(dto);
//    }
//
//    //결제 정보 입력 페이지
//    @GetMapping("/payment")
//    public PaymentResDto findUserData(@RequestParam Long userId){
//        return userService.findUserData(userId);
//    }
//
//    //결제 결과에 따른 상태 수정
//    @PatchMapping("/payment/result")
//    public Status paymentResult(@RequestBody PaymentReqDto dto){
//        return paymentService.paymentResult(dto);
//    }
//}
