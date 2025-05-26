package com.freelance.service;

import com.freelance.model.PortfolioCommentRequest;
import com.freelance.model.PortfolioCommentResponse;

public interface IPortfolioCommentService {

    public PortfolioCommentResponse addComment(Long portfolioId, PortfolioCommentRequest request);
}
