package com.freelance.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobFilterRequest {

    private Long categoryId;
    private Long subCategoryId;
    private Long serviceTypeId;

    private Double minPrice;
    private Double maxPrice;

    private Integer maxDeliveryDays;
}
