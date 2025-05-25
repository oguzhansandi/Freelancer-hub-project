package com.freelance.service.impl;

import com.freelance.exception.BaseException;
import com.freelance.exception.ErrorMessage;
import com.freelance.exception.MessageType;
import com.freelance.model.SkillRequest;
import com.freelance.model.SkillResponse;
import com.freelance.model.User;
import com.freelance.model.UserSkill;
import com.freelance.repository.SkillsRepository;
import com.freelance.repository.UserRepository;
import com.freelance.service.ISkillsService;
import com.freelance.services.CommonService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class SkillServiceImpl implements ISkillsService {

    @Autowired
    private CommonService commonService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SkillsRepository skillsRepository;

    @Override
    public SkillResponse addSkill(SkillRequest request) {
        String username = commonService.getCurrentUsername();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new BaseException(
                        new ErrorMessage(MessageType.USER_NOT_FOUND, "username : " + username)
                ));
        UserSkill skill = skillsRepository.findByName(request.getName())
                .orElseGet(() -> {
                    UserSkill newSkill = new UserSkill();
                    newSkill.setName(request.getName());
                    newSkill.setCreateDate(new Date());
                    return skillsRepository.save(newSkill);
                });

        if(!skill.getUsers().contains(user)){
            skill.getUsers().add(user);
            user.getSkills().add(skill);
            skillsRepository.save(skill);
        }
        SkillResponse response = new SkillResponse();
        BeanUtils.copyProperties(skill, response);
        response.setUsernames(
                skill.getUsers()
                        .stream()
                        .map(User::getUsername)
                        .toList()
        );
        return response;
    }
}
