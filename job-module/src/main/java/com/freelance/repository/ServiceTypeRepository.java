package com.freelance.repository;

import com.freelance.model.ServiceType;
import com.freelance.model.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ServiceTypeRepository extends JpaRepository<ServiceType, Long> {

    Optional<ServiceType> findByNameAndSubCategory(String name, SubCategory subCategory);
}
