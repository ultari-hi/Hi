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

    public void saveAll(List<AccommodationImage> images) {
        images.forEach(this::save);
    }

}
