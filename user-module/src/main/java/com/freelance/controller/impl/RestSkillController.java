package com.freelance.controller.impl;

import com.freelance.controller.IRestSkillController;
import com.freelance.controller.RootEntity;
import com.freelance.model.BaseEntity;
import com.freelance.model.SkillRequest;
import com.freelance.model.SkillResponse;
import com.freelance.service.ISkillsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest/api/skills")
public class RestSkillController extends BaseEntity implements IRestSkillController {

    @Autowired
    private ISkillsService skillsService;

    @Override
    @PostMapping("/add")
    public RootEntity<SkillResponse> addSkill(@RequestBody SkillRequest request) {
        return RootEntity.ok(skillsService.addSkill(request));
    }
}
