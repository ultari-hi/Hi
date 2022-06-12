package com.hi.service;

import com.hi.domain.Accommodation;
import com.hi.domain.Room;
import com.hi.domain.RoomImage;
import com.hi.dto.ImageDto;
import com.hi.dto.RoomReqDto;
import com.hi.dto.RoomResDto;
import com.hi.repository.AccommodationRepositoryTest;
import com.hi.repository.RoomImageRepository;
import com.hi.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toUnmodifiableList;

@Service
@Transactional
@RequiredArgsConstructor
public class RoomService {

    private final AccommodationRepositoryTest accommodationRepository;
    private final RoomRepository roomRepository;
    private final RoomImageRepository roomImageRepository;

    //객실, 사진 등록
    public void saveRoom(Long accommodationId, RoomReqDto dto) {
        Accommodation accommodation = accommodationRepository.findById(accommodationId);
        Room room = Room.createRoom(dto,accommodation);
        Room roomId = roomRepository.save(room);
        List<RoomImage> images = dto.getUrlList()
                .stream()
                .map(url -> RoomImage.create(roomId, url))
                .collect(Collectors.toUnmodifiableList());
        roomImageRepository.saveAll(images);
    }

    //객실 리스트
    public List<RoomResDto> findRoom(Long accommodationId){
        return new ArrayList<>(roomRepository.findAll(accommodationId))
                .stream()
                .map(RoomResDto::new)
                .collect(toUnmodifiableList());
    }

   //객실 사진 조회
    public ImageDto roomImages(Long roomId) {
        List<String> urlList = roomImageRepository.findAll(roomId)
                .stream()
                .map(RoomImage::getUrl)
                .collect(toUnmodifiableList());
        return new ImageDto(urlList);
    }

    //객실 수정
    public void modifyRoom(Long roomId, RoomReqDto dto) {
        Room room = roomRepository.findById(roomId);
        room.update(dto);
    }
}
