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

    public List<Accommodation> findAvailableAccommodations(List<Long> unAvailableRoomIds, int numberPeople, String region, List<String> accommodationFiltering, List<String> roomFiltering) {
        StringBuilder aFiltering = new StringBuilder("%");
        for (String keyword : accommodationFiltering) {
            aFiltering.append(keyword).append("%");
        }

        StringBuilder rFiltering = new StringBuilder("%");
        for (String keyword : roomFiltering) {
            rFiltering.append(keyword).append("%");
        }

        return em.createQuery("select distinct a from Room r join Accommodation a on r.accommodation = a " +
                        "where r.id not in :roomIds " +
                        "and r.numberPeople >= :numberPeople " +
                        "and a.region = :region " +
                        "and a.filtering like :accommodationFiltering " +
                        "and r.filtering like :roomFiltering", Accommodation.class)
                .setParameter("roomIds", unAvailableRoomIds)
                .setParameter("numberPeople", numberPeople)
                .setParameter("region", region)
                .setParameter("accommodationFiltering", aFiltering.toString())
                .setParameter("roomFiltering", rFiltering.toString())
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
