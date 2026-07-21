package com.epiis.hire.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.epiis.hire.entity.EntityJobOffer;

@Repository
public interface RepositoryJobOffer extends JpaRepository<EntityJobOffer, String> {

    @Query("SELECT j FROM EntityJobOffer j "
         + "LEFT JOIN FETCH j.parentCompany "
         + "LEFT JOIN FETCH j.parentCategory "
         + "WHERE j.status = :status "
         + "ORDER BY j.publishedAt DESC")
    List<EntityJobOffer> findAllPublished(String status);

    @Query("SELECT j FROM EntityJobOffer j "
         + "LEFT JOIN FETCH j.parentCategory "
         + "WHERE j.idCompany = :idCompany "
         + "ORDER BY j.createdAt DESC")
    List<EntityJobOffer> findByCompanyWithDetails(String idCompany);

    @Query("SELECT j FROM EntityJobOffer j "
         + "LEFT JOIN FETCH j.parentCompany "
         + "LEFT JOIN FETCH j.parentCategory "
         + "WHERE j.idJobOffer = :idJobOffer")
    Optional<EntityJobOffer> findByIdWithDetails(String idJobOffer);
}
