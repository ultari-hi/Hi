package com.hi.service;

import com.hi.domain.Accommodation;
import com.hi.domain.Room;
import com.hi.domain.RoomImage;
import com.hi.dto.ImageDto;
import com.hi.dto.RoomReqDto;
import com.hi.dto.RoomResDto;
import com.hi.repository.AccommodationRepository;
import com.hi.repository.RoomImageRepository;
import com.hi.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toUnmodifiableList;

@Service
@Transactional
@RequiredArgsConstructor
public class RoomService {

    private final AccommodationRepository accommodationRepository;
    private final RoomRepository roomRepository;
    private final RoomImageRepository roomImageRepository;

    //객실, 사진 등록
    public void createRoom(Long accommodationId, RoomReqDto dto) {
        Accommodation accommodation = accommodationRepository.findById(accommodationId)
                .orElseThrow(()->new IllegalArgumentException("숙소를 찾을 수 없습니다."));

        Room room = roomRepository.save(Room.newRoom(dto,accommodation));

        List<RoomImage> images = dto.getImageUrls()
                .stream()
                .map(url -> RoomImage.create(room, url))
                .collect(Collectors.toUnmodifiableList());
        roomImageRepository.saveAll(images);
    }

    //객실 수정
    public void modifyRoom(Long roomId, RoomReqDto dto) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("객실을 찾을 수 없습니다."));
        room.update(dto);
    }
}
