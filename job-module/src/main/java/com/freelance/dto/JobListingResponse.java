package com.freelance.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JobListingResponse {
    private Long jobId;
    private String title;
    private String description;
    private String categoryName;
    private String subCategoryName;
    private String serviceTypeName;
    private String employerName;
}
