package com.freelance.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceTypeRequest {
    private String name;
    private Long subCategoryId;
}
