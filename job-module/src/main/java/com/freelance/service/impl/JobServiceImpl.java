package com.freelance.service.impl;

import com.freelance.dto.*;
import com.freelance.enums.ServicePackageType;
import com.freelance.exception.BaseException;
import com.freelance.exception.ErrorMessage;
import com.freelance.exception.MessageType;
import com.freelance.model.*;
import com.freelance.repository.*;
import com.freelance.service.IJobService;
import com.freelance.services.CommonService;
import com.freelance.specification.JobContentSpecification;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

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

    @Override
    public List<JobListingResponse> getJobs(JobFilterRequest filter) {
        Specification<JobContent> spec = Specification.where(null);

        if (filter.getCategoryId() != null) {
            spec = spec.and(JobContentSpecification.hasCategory(filter.getCategoryId()));
        }
        if (filter.getSubCategoryId() != null) {
            spec = spec.and(JobContentSpecification.hasSubCategory(filter.getSubCategoryId()));
        }
        if (filter.getServiceTypeId() != null) {
            spec = spec.and(JobContentSpecification.hasServiceType(filter.getServiceTypeId()));
        }

        List<JobContent> jobContents = jobContentRepository.findAll(spec);

        return jobContents.stream().map(content -> {
            JobListingResponse dto = new JobListingResponse();
            dto.setJobId(content.getJobPosting().getId());
            dto.setTitle(content.getTitle());
            dto.setDescription(content.getDescription());
            dto.setEmployerName(content.getJobPosting().getEmployer().getFirstName() + " " + content.getJobPosting().getEmployer().getLastName());
            dto.setCategoryName(content.getJobPosting().getCategory().getName());
            dto.setSubCategoryName(content.getJobPosting().getSubCategory().getName());
            dto.setServiceTypeName(content.getJobPosting().getServiceType().getName());
            return dto;
        }).toList();
    }

    private FeatureDefinition findFeatureDefinitionById(Long id) {
        return featureDefinitionRepository.findById(id).orElseThrow(() ->
                new BaseException(new ErrorMessage(MessageType.DATA_NOT_FOUND, "FeatureDefinition bulunamadı: " + id)));
    }

    @Override
    public JobPostingResponse updateJob(JobPostingRequest request, Long id) {
        String username = commonService.getCurrentUsername();
        JobPosting jobPosting = jobPostingRepository.findById(id).orElseThrow(() ->
                new BaseException(new ErrorMessage(MessageType.DATA_NOT_FOUND, "İş ilanı bulunamadı.")));

        // Yetki kontrolü
        if (!username.equals(jobPosting.getEmployer().getUsername())) {
            throw new BaseException(new ErrorMessage(MessageType.ACCESS_DENIED, "Bu işlemi yapmaya yetkiniz yok."));
        }

        // 1. Ana bilgileri güncelle (kategori, alt kategori, servis tipi)
        updateBasicJobInfo(request, jobPosting);

        // 2. İçerik bilgilerini güncelle
        updateJobContent(request, jobPosting);

        // 3. Paket bilgilerini güncelle (BASIC, STANDARD, PRO)
        updateServicePackages(request, jobPosting);

        // 4. Response oluştur ve döndür
        return createJobResponse(jobPosting);
    }

    @Override
    public String deleteJob(Long id) {
        JobPosting jobPosting = jobPostingRepository.findById(id)
                .orElseThrow(() -> new BaseException(
                        new ErrorMessage(MessageType.DATA_NOT_FOUND, "İş bulunamadı")
                ));
        String username = commonService.getCurrentUsername();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new BaseException(
                        new ErrorMessage(MessageType.USER_NOT_FOUND, "Kullanıcı bulunamadı : " + username)
                ));
        if (!user.getId().equals(jobPosting.getEmployer().getId())){
            throw new BaseException(new ErrorMessage(MessageType.ACCESS_DENIED, "Bu iş ilanını silme yetkiniz yok."));
        }

        jobPostingRepository.delete(jobPosting);
        return "İş ilanı silindi";
    }

    private void updateBasicJobInfo(JobPostingRequest request, JobPosting jobPosting) {
        if (request.getCategoryId() != null) {
            Category category = categoryRepository.findById(request.getCategoryId()).orElseThrow(() ->
                    new BaseException(new ErrorMessage(MessageType.DATA_NOT_FOUND, "Kategori bulunamadı.")));
            jobPosting.setCategory(category);
        }

        if (request.getSubCategoryId() != null) {
            SubCategory subCategory = subCategoryRepository.findById(request.getSubCategoryId()).orElseThrow(() ->
                    new BaseException(new ErrorMessage(MessageType.DATA_NOT_FOUND, "Alt kategori bulunamadı.")));
            jobPosting.setSubCategory(subCategory);
        }

        if (request.getServiceTypeId() != null) {
            ServiceType serviceType = serviceTypeRepository.findById(request.getServiceTypeId()).orElseThrow(() ->
                    new BaseException(new ErrorMessage(MessageType.DATA_NOT_FOUND, "Servis tipi bulunamadı.")));
            jobPosting.setServiceType(serviceType);
        }

        jobPostingRepository.save(jobPosting);
    }

    private void updateJobContent(JobPostingRequest request, JobPosting jobPosting) {
        JobContent jobContent = jobContentRepository.findByJobPosting(jobPosting).orElseGet(JobContent::new);
        JobContentRequest contentRequest = request.getContent();

        if (contentRequest != null) {
            if (contentRequest.getTitle() != null) jobContent.setTitle(contentRequest.getTitle());
            if (contentRequest.getDescription() != null) jobContent.setDescription(contentRequest.getDescription());
            if (contentRequest.getRequirements() != null)
                jobContent.setRequirements(contentRequest.getRequirements());

            jobContent.setJobPosting(jobPosting);
            jobContentRepository.save(jobContent);
        }
    }

    private void updateServicePackages(JobPostingRequest request, JobPosting jobPosting) {
        if (request.getPackages() == null || request.getPackages().isEmpty()) {
            return;
        }

        Map<ServicePackageType, ServicePackage> existingPackages = servicePackageRepository
                .findByJobPostingId(jobPosting.getId())
                .stream()
                .collect(Collectors.toMap(ServicePackage::getType, p -> p));

        for (ServicePackageRequest packageRequest : request.getPackages()) {
            ServicePackageType type = packageRequest.getType();

            if (existingPackages.containsKey(type)) {
                updateExistingPackage(packageRequest, existingPackages.get(type));
            } else {
                createNewPackage(packageRequest, jobPosting);
            }
        }
    }

    private void updateExistingPackage(ServicePackageRequest request, ServicePackage existingPackage) {
        if (request.getTitle() != null) existingPackage.setTitle(request.getTitle());
        if (request.getDescription() != null) existingPackage.setDescription(request.getDescription());
        if (request.getPrice() != null) existingPackage.setPrice(request.getPrice());
        if (request.getRevisionCount() != null) existingPackage.setRevisionCount(request.getRevisionCount());
        if (request.getDeliveryDays() != null) existingPackage.setDeliveryTimeInDays(request.getDeliveryDays());

        servicePackageRepository.save(existingPackage);

        if (request.getFeatures() != null) {
            updatePackageFeatures(request.getFeatures(), existingPackage);
        }
    }

    private void updatePackageFeatures(List<ServiceFeatureRequest> features, ServicePackage servicePackage) {
        List<ServiceFeature> oldFeatures = serviceFeatureRepository.findByServicePackage(servicePackage);
        serviceFeatureRepository.deleteAll(oldFeatures);

        saveServiceFeatures(features, servicePackage);
    }

    private void createNewPackage(ServicePackageRequest request, JobPosting jobPosting) {
        ServicePackage newPackage = new ServicePackage();
        newPackage.setTitle(request.getTitle());
        newPackage.setDescription(request.getDescription());
        newPackage.setPrice(request.getPrice());
        newPackage.setJobPosting(jobPosting);
        newPackage.setType(request.getType());
        newPackage.setRevisionCount(request.getRevisionCount());
        newPackage.setDeliveryTimeInDays(request.getDeliveryDays());
        newPackage.setCreateDate(new Date());

        ServicePackage savedPackage = servicePackageRepository.save(newPackage);
        saveServiceFeatures(request.getFeatures(), savedPackage);
    }

    private JobPostingResponse createJobResponse(JobPosting jobPosting) {
        JobContent jobContent = jobContentRepository.findByJobPosting(jobPosting)
                .orElseThrow(() -> new BaseException(new ErrorMessage(MessageType.DATA_NOT_FOUND,"iş ilanı bulunamadı")));

        List<ServicePackage> packages = servicePackageRepository.findByJobPostingIdWithFeatures(jobPosting.getId());

        JobPostingResponse response = new JobPostingResponse();
        response.setId(jobPosting.getId());
        response.setCategoryName(jobPosting.getCategory().getName());
        response.setSubCategoryName(jobPosting.getSubCategory().getName());
        response.setServiceTypeName(jobPosting.getServiceType().getName());
        response.setCreatedAt(jobPosting.getCreateDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        response.setContent(modelMapper.map(jobContent, JobContentResponse.class));
        List<ServicePackageResponse> packageResponses = packages.stream()
                .map(pkg -> {
                    ServicePackageResponse r = new ServicePackageResponse();
                    r.setTitle(pkg.getTitle());
                    r.setDescription(pkg.getDescription());
                    r.setPrice(pkg.getPrice());
                    r.setDeliveryDays(pkg.getDeliveryTimeInDays());
                    r.setRevisionCount(pkg.getRevisionCount());
                    r.setType(pkg.getType().name());

                    if(pkg.getFeatures() != null) {
                        r.setFeatures(pkg.getFeatures().stream()
                                .map(f -> {
                                    ServiceFeatureResponse fr = new ServiceFeatureResponse();
                                    fr.setName(f.getFeatureDefinition().getName()); // FeatureDefinition'dan al
                                    fr.setValue(f.getValue());
                                    return fr;
                                }).toList());
                    }
                    return r;
                }).toList();

        response.setPackages(packageResponses);
        return response;
    }


}
