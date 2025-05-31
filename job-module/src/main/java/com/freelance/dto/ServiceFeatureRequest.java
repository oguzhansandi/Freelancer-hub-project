package com.freelance.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceFeatureRequest {

    private Long featureDefinitionId;
    private String value;
}
