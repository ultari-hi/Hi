package com.hi.controller;

import com.hi.dto.ReservationDto;
import com.hi.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    //예약 내역 조회
    @GetMapping("/reservation/{userId}")
    public List<ReservationDto> findHistory(@PathVariable Long userId){
        return reservationService.findHistory(userId);
    }
}
