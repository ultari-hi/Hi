package com.hi.dto;

import com.hi.domain.User;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AccommodationReqDto {
    private String nameKor;
    private String nameEng;
    private int postCode;
    private String address;
    private String location;
    private String introduction;
    private int numberOfPeople;
    private int price;
    private int priceKor;
    private String filtering;
}
