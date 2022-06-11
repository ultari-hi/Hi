package com.hi.comtroller;

import com.hi.dto.RoomReqDto;
import com.hi.dto.RoomResDto;
import com.hi.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class RoomController {
    private final RoomService roomService;

    //객실 등록
    @PostMapping("/{accommodationId}/room/new")
    public String roomSave(@RequestBody RoomReqDto dto, @PathVariable Long accommodationId){
        roomService.saveRoom(dto, accommodationId);
        return "accommodation/list";
    }

    //객실 리스트
    @GetMapping("/{accommodationId}/room")
    public List<RoomResDto> roomList(@PathVariable Long accommodationId){
        return roomService.findRoom(accommodationId);
    }
}
