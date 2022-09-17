package com.hi.controller;

import com.hi.dto.AccommodationDetailDto;
import com.hi.dto.AccommodationReqDto;
import com.hi.dto.AccommodationResDto;
import com.hi.dto.ImageDto;
import com.hi.service.AccommodationService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@CrossOrigin(origins = "*") // CORS 에러 처리 용도
@RestController
@RequiredArgsConstructor
public class AccommodationController {
    private final AccommodationService accommodationService;

    //숙소, 사진 등록
    @PostMapping("/accommodation/new")
    public String accommodationSave(@RequestBody AccommodationReqDto accommodationReqDto){
        accommodationService.saveAccommodation(accommodationReqDto);
        return "accommodation/list";
    }

    //숙소 상세 조회
    @GetMapping("/accommodation/{accommodationId}")
    public AccommodationDetailDto accommodationDetail(@PathVariable Long accommodationId){
        return accommodationService.findOne(accommodationId);
    }

    //필터링 한 숙소 리스트
    @GetMapping("/accommodation/list")
    public List<AccommodationResDto> findAccommodations(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkInDate,
                                                       @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkOutDate,
                                                       @RequestParam int numberOfPeople,
                                                       @RequestParam String region){
        return accommodationService.findAccommodations(checkInDate, checkOutDate, numberOfPeople, region);
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