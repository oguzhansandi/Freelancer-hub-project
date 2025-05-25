package com.freelance.service;

import com.freelance.model.PortfolioRequest;
import com.freelance.model.PortfolioResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IPortfolioService {

    public String uploadFile(String path, MultipartFile file) throws IOException;

    public PortfolioResponse createPortfolio(PortfolioRequest request,MultipartFile file);

}
