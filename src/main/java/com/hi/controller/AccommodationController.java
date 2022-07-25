package com.hi.controller;

import com.hi.dto.AccommodationReqDto;
import com.hi.dto.AccommodationResDto;
import com.hi.dto.ImageDto;
import com.hi.service.AccommodationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
    public AccommodationResDto accommodationDetail(@PathVariable Long accommodationId){
        return accommodationService.findOne(accommodationId);
    }

    //숙소 리스트
    @GetMapping("/accommodation/list")
    public List<AccommodationResDto> accommodationList(){
        return accommodationService.accommodationList();
    }

    //숙소 수정
    @PutMapping("/accommodation/{accommodationId}")
    public void modifyAccommodation(@PathVariable Long accommodationId, @RequestBody AccommodationReqDto dto){
        accommodationService.modifyAccommodation(accommodationId, dto);
    }

    //숙소 사진 조회
    @GetMapping("/accommodation/{accommodationId}/image")
    public ImageDto findImages(@PathVariable Long accommodationId){
        return accommodationService.accommodationImages(accommodationId);
    }

}