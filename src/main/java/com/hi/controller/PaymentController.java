package com.hi.controller;

import com.hi.dto.PaymentReqDto;
import com.hi.dto.ReservationDto;
import com.hi.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/payment/new")
    public String newPayment(PaymentReqDto paymentReqDto, ReservationDto reservationDto, Long userId, Long accommodationId, Long roomId){
        paymentService.newPayment(paymentReqDto, reservationDto, userId, accommodationId, roomId);
        return "1";
    }
}
