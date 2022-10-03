package com.hi.controller;

import com.hi.dto.ImageDto;
import com.hi.dto.RoomReqDto;
import com.hi.dto.RoomResDto;
import com.hi.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*") // CORS 에러 처리 용도
@RestController
@RequiredArgsConstructor
public class RoomController {
    private final RoomService roomService;

    //객실, 사진 등록
    @PostMapping("/accommodation/{accommodationId}/room/new")
    public String createRoom(@PathVariable Long accommodationId, @RequestBody RoomReqDto roomReqDto){
        roomService.createRoom(accommodationId, roomReqDto);
        return "accommodation/list";
    }

    //객실 수정
    @PutMapping("/room/{roomId}")
    public void modifyRoom(@PathVariable Long roomId, @RequestBody RoomReqDto dto){
        roomService.modifyRoom(roomId, dto);
    }
}
