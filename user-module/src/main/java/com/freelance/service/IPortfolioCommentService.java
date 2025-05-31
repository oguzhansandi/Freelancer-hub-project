package com.freelance.service;

import com.freelance.model.PortfolioCommentRequest;
import com.freelance.model.PortfolioCommentResponse;

import java.util.List;

public interface IPortfolioCommentService {

    public PortfolioCommentResponse addComment(Long portfolioId, PortfolioCommentRequest request);
    public List<PortfolioCommentResponse> getCommentList(Long portfolioId);
}
