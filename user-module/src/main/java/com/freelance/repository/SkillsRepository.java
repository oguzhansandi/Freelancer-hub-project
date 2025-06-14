package com.freelance.repository;

import com.freelance.model.user.UserSkill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SkillsRepository extends JpaRepository<UserSkill, Long> {

    Optional<UserSkill> findByName(String name);
}

