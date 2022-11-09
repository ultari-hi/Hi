package com.hi.repository;

import com.hi.domain.Room;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class RoomRepository {
    private final EntityManager em;

    public Room save(Room room) {
        em.persist(room);
        em.refresh(room);
        return room;
    }

    public List<Room> findAll(Long accommodationId) {
        return em.createQuery("select r from Room r where r.accommodation.id = :accommodationId", Room.class)
                .setParameter("accommodationId", accommodationId)
                .getResultList();
    }

    public Optional<Room> findById(Long id) {
        return Optional.ofNullable(em.createQuery("select r from Room r where r.id =:id",Room.class)
                .setParameter("id",id)
                .getSingleResult());
    }
}
