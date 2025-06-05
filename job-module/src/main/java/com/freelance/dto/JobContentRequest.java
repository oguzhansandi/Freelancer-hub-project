package com.freelance.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobContentRequest {
    private String title;
    private String description;
    private String requirements;
}
