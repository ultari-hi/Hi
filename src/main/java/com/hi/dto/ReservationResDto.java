package com.hi.dto;

import com.hi.domain.Room;
import com.hi.domain.User;
import lombok.Data;

@Data
public class ReservationResDto {
    private Long roomId;
    private String userNameKor;
    private String phoneNumber;
    private String accommodationName;
    private String roomName;
    private int price;

    public ReservationResDto(User user, Room room) {
        this.roomId = room.getId();
        this.userNameKor = user.getLastNameKor()+user.getFirstNameKor();
        this.accommodationName = room.getAccommodation().getNameKor();
        this.roomName = room.getName();
        this.phoneNumber = user.getPhoneNumber();
        this.price = room.getPriceKor();
    }
}
