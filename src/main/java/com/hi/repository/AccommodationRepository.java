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
        return accommodation;
    }

    public List<Accommodation> findAccommodationList(List<Long> accommodationIds, String region){
        return em.createQuery("select a from Accommodation a " +
                        "where a.id in :accommodationIds " +
                        "and a.region = :region",Accommodation.class)
                .setParameter("accommodationIds",accommodationIds)
                .setParameter("region",region)
                .getResultList();
    }

    public Optional<Accommodation> findById(Long id) {
        return Optional.ofNullable(em.createQuery("select a from Accommodation a where a.id = :id",Accommodation.class)
                .setParameter("id",id)
                .getSingleResult()
        );
    }
}
