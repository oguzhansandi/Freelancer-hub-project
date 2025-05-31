package com.freelance.dto;

import com.freelance.enums.ServicePackageType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServicePackageRequest {

    private String title;
    private String description;
    private double price;
    private int deliveryDays;
    private int revisionCount;
    private ServicePackageType type;
    private List<ServiceFeatureRequest> features;
}
