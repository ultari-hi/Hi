package com.hi.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class RoomImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_image_id", columnDefinition = "bigint")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;

    @Column(name = "url", columnDefinition = "text", nullable = false)
    private String url;

    public RoomImage(Room room, String url) {
        this.room = room;
        this.url = url;
    }

    public static RoomImage newRoomImage(Room room, String url){
        return new RoomImage(room, url);
    }
}
