package com.freelance.controller.impl;

import com.freelance.controller.IRestPortfolioController;
import com.freelance.controller.RootEntity;
import com.freelance.model.common.BaseEntity;
import com.freelance.model.PortfolioRequest;
import com.freelance.model.PortfolioResponse;
import com.freelance.service.IPortfolioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/rest/api/portfolio")
public class RestPortfolioControllerImpl extends BaseEntity implements IRestPortfolioController {

    @Autowired
    private IPortfolioService portfolioService;

    @Override
    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public RootEntity<PortfolioResponse> createPortfolio(@ModelAttribute PortfolioRequest request,
                                                         @RequestPart("file") MultipartFile file) {
        return RootEntity.ok(portfolioService.createPortfolio(request, file));
    }

    @Override
    @GetMapping("/{username}")
    public RootEntity<List<PortfolioResponse>> getPortfolio(@PathVariable("username") String username) {
        return RootEntity.ok(portfolioService.getPortfolio(username));
    }

    @Override
    @DeleteMapping("/{id}")
    public RootEntity<String> deletePortfolio(@PathVariable("id") Long id) {
        portfolioService.deletePortfolio(id);
        return RootEntity.ok("Porfolio başarıyla silindi");
    }
}
