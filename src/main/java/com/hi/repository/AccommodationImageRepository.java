package com.hi.repository;

import com.hi.domain.AccommodationImage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class AccommodationImageRepository {
    private final EntityManager em;

    public void save(AccommodationImage image) {
            em.persist(image);
    }

    public List<AccommodationImage> findAll(Long accommodationId) {
        return em.createQuery("select ai from AccommodationImage ai where ai.accommodation.id = :accommodationId", AccommodationImage.class)
                .setParameter("accommodationId", accommodationId)
                .getResultList();
    }

    public void saveAll(List<AccommodationImage> images) {
        images.forEach(this::save);
    }

}
