package com.hi.repository;

import com.hi.domain.User;
import com.hi.dto.FindPasswordReqDto;
import com.hi.dto.FindUsernameReqDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
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

    //회원탈퇴
    public String delete(Long id){
        em.createQuery("delete from User u where u.id = :id")
                .setParameter("id", id)
                .executeUpdate();
        return "회원탈퇴";
    }

    //아이디 중복검사
    public Optional<User> checkDuplicateUsername(String username){
        return Optional.ofNullable(em.createQuery("select u from User u where u.username = :username", User.class)
                .setParameter("username", username)
                .getSingleResult());
    }

    //닉네임 중복검사
    public Optional<String> findNickname(String nickname){
        return Optional.ofNullable(em.createQuery("select u.nickname from User u where u.nickname = :nickname", String.class)
                .setParameter("nickname", nickname)
                .getSingleResult());
    }

    //이메일 중복검사
    public Optional<String> checkDuplicateEmail(String email){
        try {
            return Optional.ofNullable(em.createQuery("select u.email from User u where u.email = :email", String.class)
                    .setParameter("email", email)
                    .getSingleResult());
        } catch (NoResultException nre) {
            return Optional.empty();
        }
    }
}
