package com.freelance.dto;

import com.freelance.enums.applications.ApplicationStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationReplyRequest {

    private ApplicationStatus status;
}
