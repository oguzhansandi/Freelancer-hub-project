package com.freelance.controller;

import com.freelance.model.PortfolioCommentRequest;
import com.freelance.model.PortfolioCommentResponse;

public interface IRestPortfolioCommentController {
    public RootEntity<PortfolioCommentResponse> addComment(Long portfolioId,PortfolioCommentRequest request);
}
