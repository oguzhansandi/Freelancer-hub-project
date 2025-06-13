package com.freelance.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServicePackageResponse {
    private String title;
    private String description;
    private Double price;
    private Integer deliveryDays;
    private Integer revisionCount;
    private String type;

    private List<ServiceFeatureResponse> features;
}

