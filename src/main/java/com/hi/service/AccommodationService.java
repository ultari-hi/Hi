package com.hi.service;

import com.hi.domain.Accommodation;
import com.hi.domain.AccommodationImage;
import com.hi.dto.AccommodationReqDto;
import com.hi.dto.AccommodationResDto;
import com.hi.dto.ImageDto;
import com.hi.repository.AccommodationImageRepository;
import com.hi.repository.AccommodationRepositoryTest;
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
public class AccommodationService {

    private final AccommodationRepositoryTest accommodationRepository;
    private final AccommodationImageRepository accommodationImageRepository;

    //숙소, 사진 등록
    public void saveAccommodation(AccommodationReqDto dto) {
        Accommodation accommodation = Accommodation.createAccommodation(dto);
        Accommodation accommodationId = accommodationRepository.save(accommodation);
        List<AccommodationImage> images = dto.getUrlList()
                .stream()
                .map(url -> AccommodationImage.create(accommodationId, url))
                .collect(Collectors.toUnmodifiableList());
        accommodationImageRepository.saveAll(images);
    }

    //숙소 상세 조회
    public AccommodationResDto findOne(Long accommodationId) {
        return new AccommodationResDto(accommodationRepository.findById(accommodationId));
    }

    //숙소 리스트
    public List<AccommodationResDto> accommodationList() {
        return new ArrayList<>(accommodationRepository.findAccommodationList()
                .stream()
                .map(AccommodationResDto::new)
                .collect(toUnmodifiableList()));
    }

    //숙소 수정
    public void modifyAccommodation(Long accommodationId, AccommodationReqDto dto) {
        Accommodation accommodation = accommodationRepository.findById(accommodationId);
        accommodation.update(dto);
    }

    //숙소 사진 조회
    public ImageDto accommodationImages(Long accommodationId) {
        List<String> urlList = accommodationImageRepository.findAll(accommodationId)
                .stream()
                .map(AccommodationImage::getUrl)
                .collect(toUnmodifiableList());
        return new ImageDto(urlList);
    }

}
