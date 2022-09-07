package com.hi.repository;

import com.hi.domain.AccommodationImage;
import com.hi.domain.Point;
import com.hi.domain.User;
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

    public List<Point> findByUserId(Long userId){
        return em.createQuery("select p from Point p where p.user = :userId",Point.class)
                .setParameter("userId",userId)
                .getResultList();
    }

    public Point findBalance(User user){
        return em.createQuery("select p from Point p where p.user = :user " +
                        "and p.isLatest = true", Point.class)
                .setParameter("user", user)
                .getSingleResult();
    }

    public void saveAll(List<Point> points) {
        points.forEach(this::save);
    }
}
