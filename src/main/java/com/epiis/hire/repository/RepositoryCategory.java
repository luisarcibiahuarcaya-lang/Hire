package com.epiis.hire.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.epiis.hire.entity.EntityCategory;

@Repository
public interface RepositoryCategory extends JpaRepository<EntityCategory, String> {

    List<EntityCategory> findByActiveTrue();
}
