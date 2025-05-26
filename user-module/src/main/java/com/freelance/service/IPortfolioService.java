package com.freelance.service;

import com.freelance.model.PortfolioRequest;
import com.freelance.model.PortfolioResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IPortfolioService {

    public String uploadFile(String path, MultipartFile file) throws IOException;

    public PortfolioResponse createPortfolio(PortfolioRequest request,MultipartFile file);

    public List<PortfolioResponse> getPortfolio(String username);

    public void deletePortfolio(Long id);
}
