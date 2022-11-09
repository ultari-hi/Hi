package com.hi.controller;

import com.hi.config.auth.CustomUserDetails;
import com.hi.dto.ReservationDto;
import com.hi.dto.ReservationReqDto;
import com.hi.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    //숙소 예약하기
    @PostMapping("/reservation/new")
    public String reserveRoom(@AuthenticationPrincipal CustomUserDetails user, @RequestBody ReservationReqDto dto) {
        return reservationService.reserveRoom(user.getUser(), dto);
    }

    //예약 내역 조회
    @GetMapping("/reservation/history")
    public List<ReservationDto> findHistory(@AuthenticationPrincipal CustomUserDetails user){
        return reservationService.findHistory(user.getUserId());
    }

    //예약 취소
    @GetMapping("/reservation/cancel/{reservationId}")
    public String cancelReservation(@AuthenticationPrincipal CustomUserDetails user, @PathVariable Long reservationId) {
        return reservationService.cancelReservation(user.getUser(), reservationId);
    }
}
