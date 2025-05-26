package com.freelance.controller;

import com.freelance.model.PortfolioRequest;
import com.freelance.model.PortfolioResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IRestPortfolioController {

    public RootEntity<PortfolioResponse> createPortfolio(PortfolioRequest request,  MultipartFile file);

    public RootEntity<List<PortfolioResponse>> getPortfolio(String username);

    public RootEntity<String> deletePortfolio(Long id);
}
