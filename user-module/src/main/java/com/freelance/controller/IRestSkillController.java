package com.freelance.controller;

import com.freelance.model.SkillRequest;
import com.freelance.model.SkillResponse;

public interface IRestSkillController {

    public RootEntity<SkillResponse> addSkill(SkillRequest request);
}
