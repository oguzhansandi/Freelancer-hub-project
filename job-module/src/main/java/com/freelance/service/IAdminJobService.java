package com.freelance.service;

import com.freelance.dto.*;
import com.freelance.model.job.FeatureDefinition;

public interface IAdminJobService {
    public CategoryResponse createCategory(CategoryRequest request);

    public SubCategoryResponse createSubCategory(SubCategoryRequest request);

    public ServiceTypeResponse createServiceType(ServiceTypeRequest request);

    public FeatureDefinition createFeatureDefinition(FeatureDefinitionRequest request);
}
