package com.freelance.service;

import com.freelance.model.SkillRequest;
import com.freelance.model.SkillResponse;
import com.freelance.model.UserSkill;

public interface ISkillsService {

    public SkillResponse addSkill(SkillRequest request);
}
