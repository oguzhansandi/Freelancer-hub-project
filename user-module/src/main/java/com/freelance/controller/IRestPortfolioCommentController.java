package com.freelance.controller;

import com.freelance.model.PortfolioCommentRequest;
import com.freelance.model.PortfolioCommentResponse;

import java.util.List;

public interface IRestPortfolioCommentController {
    public RootEntity<PortfolioCommentResponse> addComment(Long portfolioId,PortfolioCommentRequest request);
    public RootEntity<List<PortfolioCommentResponse>> getCommentList(Long portfolioId);
}
