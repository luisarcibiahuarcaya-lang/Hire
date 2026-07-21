package com.epiis.hire.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.epiis.hire.entity.EntityResume;

@Repository
public interface RepositoryResume extends JpaRepository<EntityResume, String> {

    Optional<EntityResume> findByIdUser(String idUser);
}
