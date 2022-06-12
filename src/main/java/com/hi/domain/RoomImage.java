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
    @Column(name = "room_picture_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;

    private String url;

    public RoomImage(Long id, Room room, String url) {
        this.id = id;
        this.room = room;
        this.url = url;
    }

    public RoomImage(Room room, String url) {
        this.room = room;
        this.url = url;
    }

    public static RoomImage create(Room room, String url){
        return new RoomImage(room, url);
    }
}
