package com.freelance.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DtoUser {

    private String username;

    private String password;

    private String firstName;

    private String lastName;

    private String mail;

    private String jobTitle;

    private String description;

    private String phoneNumber;
}
