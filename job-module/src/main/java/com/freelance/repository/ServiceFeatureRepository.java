package com.freelance.repository;

import com.freelance.model.job.ServiceFeature;
import com.freelance.model.job.ServicePackage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServiceFeatureRepository extends JpaRepository<ServiceFeature,Long> {

    List<ServiceFeature> findByServicePackage(ServicePackage servicePackage);

}
