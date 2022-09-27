package com.hi.repository;

import com.hi.domain.Accommodation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class AccommodationRepository {

    private final EntityManager em;

    public Accommodation save(Accommodation accommodation) {
        em.persist(accommodation);
        em.refresh(accommodation);
        return accommodation;
    }

    public List<Accommodation> findAvailableAccommodations(List<Long> unAvailableRoomIds, int numberPeople, String region) {
        return em.createQuery("select distinct a from Room r join Accommodation a on r.accommodation = a " +
                        "where r.id not in :roomIds " +
                        "and r.numberPeople >= :numberPeople " +
                        "and a.region = :region", Accommodation.class)
                .setParameter("roomIds", unAvailableRoomIds)
                .setParameter("numberPeople", numberPeople)
                .setParameter("region", region)
                .getResultList();
    }

    public List<Accommodation> findAll() {
        return em.createQuery("select a from Accommodation a ", Accommodation.class)
                .getResultList();
    }

    public Optional<Accommodation> findById(Long id) {
        return Optional.ofNullable(em.createQuery("select a from Accommodation a " +
                        "where a.id = :id",Accommodation.class)
                .setParameter("id", id)
                .getSingleResult()
        );
    }
}
