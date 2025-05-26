package com.freelance.repository;

import com.freelance.model.PortfolioComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PortfolioCommentRepository extends JpaRepository<PortfolioComment, Long> {
}
