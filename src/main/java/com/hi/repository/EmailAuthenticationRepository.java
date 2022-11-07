package com.hi.repository;

import com.hi.domain.EmailAuthentication;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class EmailAuthenticationRepository {

    private final EntityManager em;

    public void save(EmailAuthentication emailAuthentication) {
        em.persist(emailAuthentication);
    }

    public EmailAuthentication findByEmail(String email){
        try {
            return em.createQuery("select e from EmailAuthentication e where e.isLatest = true" +
                            " and e.email = :email", EmailAuthentication.class)
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

}