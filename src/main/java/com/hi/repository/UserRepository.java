package com.hi.repository;

import com.hi.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final EntityManager em;

    public Long save(User user) {
        em.persist(user);
        return user.getId();
    }

    public Optional<User> findById(Long id) {
        return Optional.ofNullable(em.createQuery("select u from User u where u.id = :id",User.class)
                .getSingleResult()) ;
    }

    public List<User> findAll() {
        return em.createQuery("select u from User u", User.class)
                .getResultList();
    }
}
