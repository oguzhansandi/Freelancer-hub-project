package com.freelance.repository;

import com.freelance.model.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PortfolioRepository extends JpaRepository<Portfolio,Long> {
}
