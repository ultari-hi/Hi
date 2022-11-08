package com.hi.repository;

import com.hi.domain.Accommodation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class AccommodationRepository {

    private final EntityManager em;

    public Accommodation save(Accommodation accommodation) {
        em.persist(accommodation);
        em.refresh(accommodation);
        return accommodation;
    }

    //필터링 조건에 맞는 숙소 리스트
    public List<Accommodation> findAvailableAccommodations(List<Long> unAvailableRoomIds, int numberPeople, String region, List<String> accommodationFiltering, List<String> roomFiltering) {

        String jpql = "select distinct a from Room r join Accommodation a on r.accommodation = a" +
                " where r.numberPeople >= :numberPeople" +
                " and a.region = :region";

        if (!unAvailableRoomIds.isEmpty()){
            jpql += " and r.id not in :unAvailableRoomIds";
        }

        if (accommodationFiltering != null){
            Collections.sort(accommodationFiltering);
            jpql += " and a.filtering like "+ "'%" ;
            for (String keyword : accommodationFiltering) {
                jpql += keyword +"%";
            }
            jpql += "'";
        }

        if (roomFiltering != null){
            Collections.sort(roomFiltering);
            jpql += " and r.filtering like "+ "'%" ;
            for (String keyword : roomFiltering) {
                jpql += keyword +"%";
            }
            jpql += "'";
        }

        if (!unAvailableRoomIds.isEmpty()) {
            return em.createQuery(jpql, Accommodation.class)
                    .setParameter("numberPeople", numberPeople)
                    .setParameter("region", region)
                    .setParameter("unAvailableRoomIds", unAvailableRoomIds)
                    .getResultList();
        } else {
            return em.createQuery(jpql, Accommodation.class)
                    .setParameter("numberPeople", numberPeople)
                    .setParameter("region", region)
                    .getResultList();
        }
    }

    //숙소 전체 조회
    public List<Accommodation> findAll() {
        return em.createQuery("select a from Accommodation a ", Accommodation.class)
                .getResultList();
    }

    //숙소 조회
    public Optional<Accommodation> findById(Long id) {
        return Optional.ofNullable(em.createQuery("select a from Accommodation a" +
                        " where a.id = :id",Accommodation.class)
                .setParameter("id", id)
                .getSingleResult());
    }

    //숙소 상세조회
    public Optional<Accommodation> findByCondition(Long accommodationId) {
        return Optional.ofNullable(em.createQuery("select a from Accommodation a join Room r on r.accommodation = a" +
                " where a.id = :accommodationId", Accommodation.class)
                .setParameter("accommodationId", accommodationId)
                .getSingleResult());
    }
}
