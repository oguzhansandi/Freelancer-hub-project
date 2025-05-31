package com.freelance.controller.impl;

import com.freelance.controller.IRestPortfolioCommentController;
import com.freelance.controller.RootEntity;
import com.freelance.model.BaseEntity;
import com.freelance.model.PortfolioCommentRequest;
import com.freelance.model.PortfolioCommentResponse;
import com.freelance.service.IPortfolioCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("rest/api/portfolios")
public class RestPortfolioCommentControllerImpl extends BaseEntity implements IRestPortfolioCommentController {

    @Autowired
    private IPortfolioCommentService commentService;

    @Override
    @PostMapping("/{portfolioId}/comments")
    public RootEntity<PortfolioCommentResponse> addComment(
            @PathVariable("portfolioId") Long portfolioId,
            @RequestBody PortfolioCommentRequest request) {
        return RootEntity.ok(commentService.addComment(portfolioId,request));
    }

    @Override
    @GetMapping("/{portfolioId}/comments")
    public RootEntity<List<PortfolioCommentResponse>> getCommentList(
            @PathVariable("portfolioId") Long portfolioId)
    {
        return RootEntity.ok(commentService.getCommentList(portfolioId));
    }
}
