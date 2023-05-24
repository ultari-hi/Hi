package com.hi.service;

import com.hi.domain.Accommodation;
import com.hi.domain.Room;
import com.hi.domain.RoomImage;
import com.hi.dto.accommodation.RoomReqDto;
import com.hi.repository.AccommodationRepository;
import com.hi.repository.RoomImageRepository;
import com.hi.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class RoomService {

    private final AccommodationRepository accommodationRepository;
    private final RoomRepository roomRepository;
    private final RoomImageRepository roomImageRepository;

    //객실, 사진 등록
    public String registerRoom(Long accommodationId, RoomReqDto dto) {
        Accommodation accommodation = accommodationRepository.findById(accommodationId)
                .orElseThrow(()->new NoResultException("숙소를 찾을 수 없습니다."));
//
        Room room = roomRepository.save(Room.newRoom(dto,accommodation));

        if (dto.getImageUrls() != null) {
            List<RoomImage> images = dto.getImageUrls()
                    .stream()
                    .map(url -> RoomImage.newRoomImage(room, url))
                    .collect(Collectors.toUnmodifiableList());
            roomImageRepository.saveAll(images);
        }
        return "success";
    }

    //객실 수정
    public void modifyRoom(Long roomId, RoomReqDto dto) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("객실을 찾을 수 없습니다."));
        room.update(dto);
    }
}
