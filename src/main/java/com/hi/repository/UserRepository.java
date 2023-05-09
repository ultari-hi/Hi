package com.hi.repository;

import com.hi.domain.User;
import com.hi.dto.user.FindPasswordReqDto;
import com.hi.dto.user.FindUsernameReqDto;
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
        try {
            return Optional.ofNullable(em.createQuery("select u from User u where u.id = :id",User.class)
                    .setParameter("id",id)
                    .getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    //회원탈퇴
    public String delete(Long id){
        em.createQuery("delete from User u where u.id = :id")
                .setParameter("id", id)
                .executeUpdate();
        return "success";
    }

    public Optional<User> findByUsername(String username){
        return Optional.ofNullable(em.createQuery("select u from User u where u.username = :username", User.class)
                .setParameter("username", username)
                .getSingleResult());
    }

    //아이디 중복검사
    public Optional<String> findUsername(String username){
        try {
            return Optional.ofNullable(em.createQuery("select u.username from User u where u.username = :username", String.class)
                    .setParameter("username", username)
                    .getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    //닉네임 중복검사
    public Optional<String> findNickname(String nickname){
        try {
            return Optional.ofNullable(em.createQuery("select u.nickname from User u where u.nickname = :nickname", String.class)
                    .setParameter("nickname", nickname)
                    .getSingleResult());
        } catch (NoResultException nre) {
            return Optional.empty();
        }
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

    //아이디 찾기
    public Optional<String> findUsernameByEmail(FindUsernameReqDto dto){
        try {
            return Optional.ofNullable(em.createQuery("select u.username from User u where u.email = :email" +
                            " and u.birthDate = :birthDate",String.class)
                    .setParameter("email", dto.getEmail())
                    .setParameter("birthDate", dto.getBirthDate())
                    .getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    //비밀번호 찾기
    public Optional<User> findPassword(FindPasswordReqDto dto){
        try {
            return Optional.ofNullable(em.createQuery("select u from User u where u.email = :email" +
                            " and u.username = :username" +
                            " and u.birthDate = :birthDate", User.class)
                    .setParameter("email", dto.getEmail())
                    .setParameter("username", dto.getUsername())
                    .setParameter("birthDate", dto.getBirthDate())
                    .getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
}
