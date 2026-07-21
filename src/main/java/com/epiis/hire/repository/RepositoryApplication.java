package com.epiis.hire.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.epiis.hire.entity.EntityApplication;

@Repository
public interface RepositoryApplication extends JpaRepository<EntityApplication, String> {

    boolean existsByIdJobOfferAndIdUser(String idJobOffer, String idUser);

    @Query("SELECT a FROM EntityApplication a "
         + "LEFT JOIN FETCH a.parentJobOffer jo "
         + "LEFT JOIN FETCH jo.parentCompany "
         + "WHERE a.idUser = :idUser "
         + "ORDER BY a.appliedAt DESC")
    List<EntityApplication> findByUserWithDetails(String idUser);

    @Query("SELECT a FROM EntityApplication a "
         + "LEFT JOIN FETCH a.parentUser "
         + "WHERE a.idJobOffer = :idJobOffer "
         + "ORDER BY a.appliedAt DESC")
    List<EntityApplication> findByJobOfferWithDetails(String idJobOffer);

    Optional<EntityApplication> findByIdApplication(String idApplication);
}
