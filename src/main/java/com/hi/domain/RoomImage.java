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
    @Column(name = "room_imgae_id", columnDefinition = "bigint")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;

    @Column(name = "room_imgae_url", columnDefinition = "text", nullable = false)
    private String roomImageUrl;

    public RoomImage(Long id, Room room, String roomImageUrl) {
        this.id = id;
        this.room = room;
        this.roomImageUrl = roomImageUrl;
    }

    public RoomImage(Room room, String roomImageUrl) {
        this.room = room;
        this.roomImageUrl = roomImageUrl;
    }

    public static RoomImage create(Room room, String roomImageUrl){
        return new RoomImage(room, roomImageUrl);
    }
}
