package com.freelance.service.impl;

import com.freelance.dto.ApplicationRequest;
import com.freelance.dto.ApplicationResponse;
import com.freelance.enums.ApplicationStatus;
import com.freelance.exception.BaseException;
import com.freelance.exception.ErrorMessage;
import com.freelance.exception.MessageType;
import com.freelance.model.Application;
import com.freelance.model.job.JobPosting;
import com.freelance.model.user.User;
import com.freelance.repository.ApplicationRepository;
import com.freelance.repository.JobPostingRepository;
import com.freelance.repository.UserRepository;
import com.freelance.service.IApplicationService;
import com.freelance.services.CommonService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ApplicationServiceImpl implements IApplicationService {

    @Autowired
    private CommonService commonService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JobPostingRepository jobPostingRepository;

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ApplicationResponse applyToJob(ApplicationRequest request) {
        String username = commonService.getCurrentUsername();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new BaseException(
                        new ErrorMessage(MessageType.USER_NOT_FOUND, "Kullanıcı bulunamadı : " + username)
                ));

        JobPosting jobPosting = jobPostingRepository.findById(request.getJobId())
                .orElseThrow(() -> new BaseException(
                        new ErrorMessage(MessageType.DATA_NOT_FOUND, "iş bulunamadı : " + request.getJobId())
                ));

        if (jobPosting.getEmployer().getId().equals(user.getId())){
            throw new BaseException(new ErrorMessage(MessageType.ACCESS_DENIED, "Kendi ilanınıza başvuramazsınız! : " + username));
        }

        boolean exists = applicationRepository.existsByJobPostingAndFreelancer(jobPosting, user);
        if (exists){
            throw new BaseException(new ErrorMessage(MessageType.ALREADY_EXISTS, "Daha önce başvuru yaptınız"));
        }

        Application application = new Application();
        application.setJobPosting(jobPosting);
        application.setCoverLetter(request.getCoverLetter());
        application.setStatus(ApplicationStatus.PENDING);
        application.setFreelancer(user);
        application.setCreateDate(new Date());
        application.setApplyDate(new Date());

        Application savedApplication = applicationRepository.save(application);
        ApplicationResponse mapped = modelMapper.map(savedApplication, ApplicationResponse.class);
        mapped.setFreelancer(username);
        return mapped;
    }

}
