package com.freelance.repository;

import com.freelance.model.Category;
import com.freelance.model.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubCategoryRepository extends JpaRepository<SubCategory, Long> {

    Optional<SubCategory> findByNameAndCategory(String name, Category category);
}
