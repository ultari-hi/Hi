package com.hi.repository;

import com.hi.domain.Accommodation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class AccommodationRepositoryTest {

    private final EntityManager em;

    public Accommodation save(Accommodation accommodation) {
            em.persist(accommodation);
            return accommodation;
    }

    public List<Accommodation> findAccommodationList(){
        return em.createQuery("select a from Accommodation a",Accommodation.class)
                .getResultList();
    }

    public Accommodation findById(Long id) {
        return em.createQuery("select a from Accommodation a where a.id = :id",Accommodation.class)
                .setParameter("id",id)
                .getSingleResult();
    }
}
