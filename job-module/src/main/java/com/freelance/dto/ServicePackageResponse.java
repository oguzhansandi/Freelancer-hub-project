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
    private double price;
    private int deliveryDays;
    private int revisionCount;
    private String type;

    private List<ServiceFeatureResponse> features;
}

