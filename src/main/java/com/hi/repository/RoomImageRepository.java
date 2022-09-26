package com.hi.repository;

import com.hi.domain.RoomImage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class RoomImageRepository {
    private final EntityManager em;

    public void save(RoomImage image) {
            em.persist(image);
    }

    public List<RoomImage> findAllById(Long roomId) {
        return em.createQuery("select ri from RoomImage ri where ri.room.id = :roomId", RoomImage.class)
                .setParameter("roomId", roomId)
                .getResultList();
    }

    public void saveAll(List<RoomImage> images) {
        images.forEach(this::save);
    }

}
