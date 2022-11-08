package com.hi.controller;

import com.hi.dto.AccommodationDetailDto;
import com.hi.dto.AccommodationReqDto;
import com.hi.dto.AccommodationResDto;
import com.hi.service.AccommodationService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class AccommodationController {
    private final AccommodationService accommodationService;

    //숙소, 사진 등록
    @PostMapping("/accommodation/new")
    public String createAccommodation(@AuthenticationPrincipal @RequestBody AccommodationReqDto accommodationReqDto){
        accommodationService.createAccommodation(accommodationReqDto);
        return "accommodation/list";
    }

    //숙소 상세 조회
    @GetMapping("/accommodation/{accommodationId}")
    public AccommodationDetailDto findAccommodationDetail(@PathVariable Long accommodationId,
                                                          @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkInDate,
                                                          @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkOutDate,
                                                          @RequestParam(required = false, defaultValue = "1") Integer numberPeople){
        return accommodationService.findAccommodationDetail(accommodationId, checkInDate, checkOutDate, numberPeople);
    }

    //필터링 한 숙소 리스트
    @GetMapping("/accommodation/list")
    public List<AccommodationResDto> findAccommodations(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkInDate,
                                                       @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkOutDate,
                                                       @RequestParam int numberPeople,
                                                       @RequestParam String region,
                                                       @RequestParam(required = false) List<String> aFiltering,
                                                        @RequestParam(required = false) List<String> rFiltering){
        return accommodationService.findAccommodations(checkInDate, checkOutDate, numberPeople, region, aFiltering, rFiltering);
    }

    //숙소 전체 리스트
    @GetMapping("/accommodation")
    public List<AccommodationResDto> findAllAccommodation(){
        return accommodationService.findAllAccommodation();
    }

    //숙소 수정
    @PutMapping("/accommodation/{accommodationId}")
    public void modifyAccommodation(@PathVariable Long accommodationId, @RequestBody AccommodationReqDto dto){
        accommodationService.modifyAccommodation(accommodationId, dto);
    }

}