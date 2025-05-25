package com.freelance.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PortfolioResponse {

    private String name;

    private String category;

    private String description;

    private int likeCount;

    private int viewCount;

    private String mediaURL;

}
