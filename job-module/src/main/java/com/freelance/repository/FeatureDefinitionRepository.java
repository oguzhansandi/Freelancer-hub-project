package com.freelance.repository;

import com.freelance.model.FeatureDefinition;
import com.freelance.model.ServiceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FeatureDefinitionRepository extends JpaRepository<FeatureDefinition, Long> {

    Optional<FeatureDefinition> findByNameAndServiceType(String name, ServiceType type);
}
