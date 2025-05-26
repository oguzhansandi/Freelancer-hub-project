package com.freelance.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PortfolioCommentResponse {

    private String user;

    private Long portfolioId;

    private String content;
}
