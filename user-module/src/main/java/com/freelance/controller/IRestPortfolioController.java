package com.freelance.controller;

import com.freelance.model.PortfolioRequest;
import com.freelance.model.PortfolioResponse;
import org.springframework.web.multipart.MultipartFile;

public interface IRestPortfolioController {

    public RootEntity<PortfolioResponse> createPortfolio(PortfolioRequest request,  MultipartFile file);

}
