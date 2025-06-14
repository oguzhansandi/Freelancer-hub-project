package com.freelance.controller.impl;

import com.freelance.controller.IRestAdminJobController;
import com.freelance.controller.RootEntity;
import com.freelance.dto.*;
import com.freelance.model.common.BaseEntity;
import com.freelance.model.job.FeatureDefinition;
import com.freelance.service.IAdminJobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest/api/admin")
public class RestAdminJobControllerImpl extends BaseEntity implements IRestAdminJobController {

    @Autowired
    private IAdminJobService adminJobService;

    @Override
    @PostMapping("/categories")
    public RootEntity<CategoryResponse> createCategory(@RequestBody CategoryRequest request) {
        return RootEntity.ok(adminJobService.createCategory(request));
    }

    @Override
    @PostMapping("/subcategories")
    public RootEntity<SubCategoryResponse> createSubCategory(@RequestBody SubCategoryRequest request) {
        return RootEntity.ok(adminJobService.createSubCategory(request));
    }

    @Override
    @PostMapping("/service-types")
    public RootEntity<ServiceTypeResponse> createServiceType(@RequestBody ServiceTypeRequest request) {
        return RootEntity.ok(adminJobService.createServiceType(request));
    }

    @Override
    @PostMapping("/feature-definitions")
    public RootEntity<FeatureDefinition> createFeatureDefinition(@RequestBody FeatureDefinitionRequest request) {
        return RootEntity.ok(adminJobService.createFeatureDefinition(request));
    }


}
