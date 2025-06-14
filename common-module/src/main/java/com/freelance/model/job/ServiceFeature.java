package com.freelance.model.job;

import com.freelance.model.common.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "service_feature")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ServiceFeature extends BaseEntity {
    private String value;

    @ManyToOne
    private ServicePackage servicePackage;

    @ManyToOne(optional = false)
    private FeatureDefinition featureDefinition;
}
