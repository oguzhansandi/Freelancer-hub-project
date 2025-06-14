package com.freelance.service.impl;

import com.freelance.dto.*;
import com.freelance.exception.BaseException;
import com.freelance.exception.ErrorMessage;
import com.freelance.exception.MessageType;
import com.freelance.model.job.Category;
import com.freelance.model.job.FeatureDefinition;
import com.freelance.model.job.ServiceType;
import com.freelance.model.job.SubCategory;
import com.freelance.repository.CategoryRepository;
import com.freelance.repository.FeatureDefinitionRepository;
import com.freelance.repository.ServiceTypeRepository;
import com.freelance.repository.SubCategoryRepository;
import com.freelance.service.IAdminJobService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AdminJobServiceImpl implements IAdminJobService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private SubCategoryRepository subCategoryRepository;

    @Autowired
    private ServiceTypeRepository serviceTypeRepository;

    @Autowired
    private FeatureDefinitionRepository featureDefinitionRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryResponse createCategory(CategoryRequest request) {
        Category category = new Category();
        category.setName(request.getName());
        category.setFreelancers(0);
        category.setJobPosts(0);
        category.setCreateDate(new Date());

        Category savedCategory = categoryRepository.save(category);
        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setName(savedCategory.getName());

        return categoryResponse;
    }

    @Override
    public SubCategoryResponse createSubCategory(SubCategoryRequest request) {
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new BaseException(
                        new ErrorMessage(MessageType.DATA_NOT_FOUND, "Kategori bulunamadı : " + request.getCategoryId())
                ));
        boolean exists = subCategoryRepository.findByNameAndCategory(request.getName(), category).isPresent();

        if (exists){
            throw new BaseException(
                    new ErrorMessage(MessageType.ALREADY_EXISTS, "Bu isimde bir alt kategori zaten mevcut")
            );
        }

        SubCategory subCategory = new SubCategory();
        subCategory.setName(request.getName());
        subCategory.setCategory(category);
        subCategory.setCreateDate(new Date());

        SubCategory savedSubCategory = subCategoryRepository.save(subCategory);

        SubCategoryResponse subCategoryResponse = new SubCategoryResponse();
        modelMapper.map(savedSubCategory, subCategoryResponse);
        subCategoryResponse.setCategory(category.getName());

        return subCategoryResponse;
    }

    @Override
    public ServiceTypeResponse createServiceType(ServiceTypeRequest request) {

        SubCategory subCategory = subCategoryRepository.findById(request.getSubCategoryId())
                .orElseThrow(() -> new BaseException(
                        new ErrorMessage(MessageType.DATA_NOT_FOUND, "Alt kategori bulunamadı : " + request.getSubCategoryId())
                ));

        boolean exists = serviceTypeRepository.findByNameAndSubCategory(request.getName(), subCategory).isPresent();
        if (exists){
            throw new BaseException(
                    new ErrorMessage(MessageType.ALREADY_EXISTS,"Servis zaten kaydedilmiş")
            );
        }

        ServiceType serviceType = new ServiceType();
        serviceType.setCreateDate(new Date());
        serviceType.setName(request.getName());
        serviceType.setSubCategory(subCategory);

        ServiceType savedServiceType = serviceTypeRepository.save(serviceType);

        ServiceTypeResponse serviceTypeResponse = new ServiceTypeResponse();
        serviceTypeResponse.setName(savedServiceType.getName());
        serviceTypeResponse.setSubCategory(savedServiceType.getSubCategory().getName());
        return serviceTypeResponse;
    }

    @Override
    public FeatureDefinition createFeatureDefinition(FeatureDefinitionRequest request) {

        ServiceType serviceType = serviceTypeRepository.findById(request.getServiceTypeId())
                .orElseThrow(() -> new BaseException(
                        new ErrorMessage(MessageType.DATA_NOT_FOUND, "Servis tipi bulunamadı : " + request.getServiceTypeId())
                ));

        boolean exists = featureDefinitionRepository.findByNameAndServiceType(request.getName(), serviceType).isPresent();

        if (exists){
            throw new BaseException(
                    new ErrorMessage(MessageType.ALREADY_EXISTS,"Özellik zaten kaydedilmiş:" + request.getName())
            );
        }

        FeatureDefinition featureDefinition = new FeatureDefinition();
        featureDefinition.setName(request.getName());
        featureDefinition.setServiceType(serviceType);
        featureDefinition.setRequired(request.isRequired());
        featureDefinition.setInputType(request.getInputType());
        featureDefinition.setCreateDate(new Date());

        featureDefinitionRepository.save(featureDefinition);

        return featureDefinition;
    }
}
