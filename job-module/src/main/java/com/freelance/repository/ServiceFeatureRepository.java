package com.freelance.repository;

import com.freelance.model.ServiceFeature;
import com.freelance.model.ServicePackage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServiceFeatureRepository extends JpaRepository<ServiceFeature,Long> {

    List<ServiceFeature> findByServicePackage(ServicePackage servicePackage);

}
