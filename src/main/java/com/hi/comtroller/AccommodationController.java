package com.hi.comtroller;

import com.hi.dto.AccommodationReqDto;
import com.hi.dto.AccommodationResDto;
import com.hi.service.AccommodationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class AccommodationController {
    private final AccommodationService accommodationService;

    //숙소 등록
    @PostMapping("/accommodation/new")
    public String accommodationSave(@RequestBody AccommodationReqDto dto){
        accommodationService.saveAccommodation(dto);
        return "accommodation/list";
    }

    //숙소 상세 조회
    @GetMapping("/accommodation/{accommodationId}")
    public AccommodationResDto accommodationDetail(@PathVariable Long accommodationId){
        return accommodationService.findOne(accommodationId);
    }

    //숙소 리스트
    @GetMapping("/accommodation/list")
    public List<AccommodationResDto> accommodationList(@RequestParam LocalDate checkInDate,
                                                       @RequestParam LocalDate checkOutDate,
                                                       @RequestParam String region,
                                                       @RequestParam int number_of_people){
        return accommodationService.accommodationList(checkInDate, checkOutDate, number_of_people, region);
    }

    //숙소 수정
    @PutMapping("/accommodation/{accommodationId}")
    public String modifyAccommodation(@PathVariable Long accommodationId, AccommodationReqDto dto){
        accommodationService.modifyAccommodation(accommodationId, dto);
        return "/accommodation/"+accommodationId;
    }

}
