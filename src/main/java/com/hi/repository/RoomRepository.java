package com.hi.repository;

import com.hi.domain.Room;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class RoomRepository {
    private final EntityManager em;

    public Room save(Room room) {
            em.persist(room);
        return room;
    }

    public List<Long> findAvailableRoom(List<Long> roomIds, int numberOfPeople) {
        return em.createQuery("select distinct r.accommodation.id from Room as r " +
                        "where r.id not in :roomIds " +
                        "and r.numberOfPeople > :numberOfPeople",Long.class)
                .setParameter("roomIds",roomIds)
                .setParameter("numberOfPeople", numberOfPeople)
                .getResultList();
    }

    public List<Room> findAll(Long accommodationId) {
        return em.createQuery("select r from Room r where r.accommodation.id = :accommodationId", Room.class)
                .setParameter("accommodationId", accommodationId)
                .getResultList();
    }

    public Room findById(Long id) {
        return em.createQuery("select r from Room r where r.id =:id",Room.class)
                .setParameter("id",id)
                .getSingleResult();
    }
}
