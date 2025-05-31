package com.freelance.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobContentResponse {
    private String title;
    private String description;
    private String requirements;
}

