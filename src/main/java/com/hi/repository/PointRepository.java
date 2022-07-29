package com.hi.repository;

import com.hi.domain.Point;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class PointRepository {

    private final EntityManager em;

    public void save(Point point){
        em.persist(point);
    }

    public List<Point> findById(Long id){
        return em.createQuery("select p from Point p where p.id = :id",Point.class)
                .getResultList();
    }
}
