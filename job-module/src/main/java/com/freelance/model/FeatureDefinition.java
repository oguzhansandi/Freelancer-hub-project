package com.freelance.model;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "feature_definition")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FeatureDefinition  extends BaseEntity{

    private String name;

    private String inputType;

    private boolean required;

    @ManyToOne
    private ServiceType serviceType;
}
