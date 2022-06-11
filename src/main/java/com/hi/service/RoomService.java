package com.hi.service;

import com.hi.domain.Accommodation;
import com.hi.domain.Room;
import com.hi.dto.RoomReqDto;
import com.hi.dto.RoomResDto;
import com.hi.repository.AccommodationRepositoryTest;
import com.hi.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toUnmodifiableList;

@Service
@Transactional
@RequiredArgsConstructor
public class RoomService {

    private final AccommodationRepositoryTest accommodationRepository;
    private final RoomRepository roomRepository;

    public void saveRoom(RoomReqDto dto, Long accommodationId) {
        Accommodation accommodation = accommodationRepository.findById(accommodationId);
        Room room = Room.createRoom(dto,accommodation);
        roomRepository.save(room);
    }

    //객실 리스트
    public List<RoomResDto> findRoom(Long accommodationId){
        return new ArrayList<>(roomRepository.findAll(accommodationId))
                .stream()
                .map(RoomResDto::new)
                .collect(toUnmodifiableList());
    }


}
