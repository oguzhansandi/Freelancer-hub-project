package com.freelance.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FeatureDefinitionRequest {

    private String name;
    private String inputType;
    private boolean required;
    private Long serviceTypeId;

}
