package com.freelance.controller;

import com.freelance.dto.*;
import com.freelance.model.job.FeatureDefinition;

public interface IRestAdminJobController {
    public RootEntity<CategoryResponse> createCategory(CategoryRequest request);

    public RootEntity<SubCategoryResponse> createSubCategory(SubCategoryRequest request);

    public RootEntity<ServiceTypeResponse> createServiceType(ServiceTypeRequest request);

    public RootEntity<FeatureDefinition> createFeatureDefinition(FeatureDefinitionRequest request);

}
