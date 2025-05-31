package com.freelance.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobPostingRequest {

    private Long categoryId;
    private Long subCategoryId;
    private Long ServiceTypeId;

    private JobContentRequest content;
    private List<ServicePackageRequest> packages;
}
