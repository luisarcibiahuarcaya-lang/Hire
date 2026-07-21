package com.epiis.hire.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.epiis.hire.entity.EntityUser;

@Repository
public interface RepositoryUser extends JpaRepository<EntityUser, String> {

    Optional<EntityUser> findByEmail(String email);

    boolean existsByEmail(String email);
}
