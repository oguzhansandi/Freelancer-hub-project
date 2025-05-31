package com.freelance.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobPostingResponse {
    private Long id;
    private String categoryName;
    private String subCategoryName;
    private String serviceTypeName;

    private JobContentResponse content;

    private List<ServicePackageResponse> packages;

    private LocalDateTime createdAt;
}
