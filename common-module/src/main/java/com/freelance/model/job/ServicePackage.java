package com.freelance.model.job;

import com.freelance.enums.ServicePackageType;
import com.freelance.model.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "service_package")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ServicePackage extends BaseEntity {

    @Enumerated(EnumType.STRING)
    private ServicePackageType type;

    private String title;

    private String description;

    private int deliveryTimeInDays;

    private int revisionCount;

    private double price;

    @ManyToOne
    private JobPosting jobPosting;

    @OneToMany(mappedBy = "servicePackage", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ServiceFeature> features;
}
