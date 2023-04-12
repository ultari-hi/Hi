package com.hi.repository;

import com.hi.domain.Review;
import com.hi.domain.Room;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ReviewRepository {
    private final EntityManager em;

    public void save(Review review){
        em.persist(review);
    }

    public Optional<List<Review>> findByRooms(List<Room> rooms) {
        return Optional.ofNullable(em.createQuery("select review from Review review" +
                " where review.room in :rooms", Review.class)
                .setParameter("rooms", rooms)
                .getResultList());
    }


}