package com.epiis.hire.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.epiis.hire.entity.EntityCompany;

@Repository
public interface RepositoryCompany extends JpaRepository<EntityCompany, String> {

    Optional<EntityCompany> findByIdUser(String idUser);
}
