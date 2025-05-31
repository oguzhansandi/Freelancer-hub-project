package com.freelance.service.impl;

import com.freelance.dto.*;
import com.freelance.exception.BaseException;
import com.freelance.exception.ErrorMessage;
import com.freelance.exception.MessageType;
import com.freelance.model.*;
import com.freelance.repository.*;
import com.freelance.service.IJobService;
import com.freelance.services.CommonService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class JobServiceImpl implements IJobService {

    @Autowired
    private CommonService commonService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JobPostingRepository jobPostingRepository;

    @Autowired
    private JobContentRepository jobContentRepository;

    @Autowired
    private ServicePackageRepository servicePackageRepository;

    @Autowired
    private FeatureDefinitionRepository featureDefinitionRepository;

    @Autowired
    private ServiceFeatureRepository serviceFeatureRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private SubCategoryRepository subCategoryRepository;

    @Autowired
    private ServiceTypeRepository serviceTypeRepository;

    @Autowired
    private ModelMapper modelMapper;

    private JobContent saveJobContent(JobContentRequest request, JobPosting jobPosting) {
        Optional<JobContent> existingContent = jobContentRepository.findByJobPosting(jobPosting);
        JobContent content = existingContent.orElseGet(JobContent::new);

        content.setTitle(request.getTitle());
        content.setDescription(request.getDescription());
        content.setRequirements(request.getRequirements());
        content.setJobPosting(jobPosting);
        content.setCreateDate(new Date());

        return jobContentRepository.save(content);
    }


    private void saveServicePackages(List<ServicePackageRequest> requests, JobPosting jobPosting) {
        for (ServicePackageRequest req : requests) {
            ServicePackage servicePackage = new ServicePackage();
            servicePackage.setTitle(req.getTitle());
            servicePackage.setDescription(req.getDescription());
            servicePackage.setPrice(req.getPrice());
            servicePackage.setJobPosting(jobPosting);
            servicePackage.setType(req.getType());
            servicePackage.setRevisionCount(req.getRevisionCount());
            servicePackage.setDeliveryTimeInDays(req.getDeliveryDays());
            servicePackage.setCreateDate(new Date());

            ServicePackage savedPackage = servicePackageRepository.save(servicePackage);

            saveServiceFeatures(req.getFeatures(),savedPackage);
        }
    }

    private FeatureDefinition findFeatureDefinitionById(Long id) {
        return featureDefinitionRepository.findById(id)
                .orElseThrow(() -> new BaseException(
                        new ErrorMessage(MessageType.DATA_NOT_FOUND, "FeatureDefinition bulunamadı: " + id)
                ));
    }

    private void saveServiceFeatures(List<ServiceFeatureRequest> features, ServicePackage servicePackage){
        for (ServiceFeatureRequest featureRequest: features){
            FeatureDefinition def = findFeatureDefinitionById(featureRequest.getFeatureDefinitionId());

            ServiceFeature sf = new ServiceFeature();
            sf.setFeatureDefinition(def);
            sf.setValue(featureRequest.getValue());
            sf.setServicePackage(servicePackage);

            serviceFeatureRepository.save(sf);
        }
    }

    private JobPosting saveJobPosting(JobPostingRequest request){
        String username = commonService.getCurrentUsername();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new BaseException(
                        new ErrorMessage(MessageType.USER_NOT_FOUND, "Kullanıcı bulunamadı: " + username)
                ));
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new BaseException(
                        new ErrorMessage(MessageType.DATA_NOT_FOUND, "Kategori bulunamadı: " + request.getCategoryId())
                ));

        SubCategory subCategory = subCategoryRepository.findById(request.getSubCategoryId())
                .orElseThrow(() -> new BaseException(
                        new ErrorMessage(MessageType.DATA_NOT_FOUND, "Alt kategori bulunamadı: " + request.getSubCategoryId())
                ));

        ServiceType serviceType = serviceTypeRepository.findById(request.getServiceTypeId())
                .orElseThrow(() -> new BaseException(
                        new ErrorMessage(MessageType.DATA_NOT_FOUND, "Servis tipi bulunamadı: " + request.getServiceTypeId())
                ));

        JobPosting jobPosting = new JobPosting();
        jobPosting.setEmployer(user);
        jobPosting.setCategory(category);
        jobPosting.setSubCategory(subCategory);
        jobPosting.setServiceType(serviceType);
        jobPosting.setCreateDate(new Date());

        return jobPostingRepository.save(jobPosting);
    }

    public JobPostingResponse jobPost(JobPostingRequest request){
        JobPosting jobPosting = saveJobPosting(request);
        saveJobContent(request.getContent(), jobPosting);
        saveServicePackages(request.getPackages(), jobPosting);

        JobPostingResponse response = new JobPostingResponse();
        response.setId(jobPosting.getId());
        response.setCategoryName(jobPosting.getCategory().getName());
        response.setSubCategoryName(jobPosting.getSubCategory().getName());
        response.setServiceTypeName(jobPosting.getServiceType().getName());
        response.setCreatedAt(jobPosting.getCreateDate()
                .toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime());

        JobContent content = saveJobContent(request.getContent(), jobPosting);
        response.setContent(modelMapper.map(content, JobContentResponse.class));

        List<ServicePackage> packages = servicePackageRepository.findByJobPostingId(jobPosting.getId());
        List<ServicePackageResponse> packageDtos = packages.stream()
                .map(pkg -> modelMapper.map(pkg, ServicePackageResponse.class))
                .toList();
        response.setPackages(packageDtos);

        return response;

    }
}
