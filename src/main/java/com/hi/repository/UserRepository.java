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
                .setParameter("id",id)
                .getSingleResult());
    }

    public List<User> findAll() {
        return em.createQuery("select u from User u", User.class)
                .getResultList();
    }

    public Optional<User> findByUsername(String username){
        return Optional.ofNullable(em.createQuery("select u from User u where u.username = :username", User.class)
                .setParameter("username", username)
                .getSingleResult());
    }

    //회원탈퇴
    public String delete(Long id){
        em.createQuery("delete from User u where u.id = :id")
                .setParameter("id", id)
                .executeUpdate();
        return "회원탈퇴";
    }

    public Optional<String> findNickname(String nickname){
        return Optional.ofNullable(em.createQuery("select u.nickname from User u where u.nickname = :nickname", String.class)
                .setParameter("nickname", nickname)
                .getSingleResult());
    }
}
