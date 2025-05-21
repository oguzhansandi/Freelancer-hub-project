package com.freelance.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class SkillResponse {

    private Long id;

    private Date createDate;

    private String name;

    private List<String> usernames;
}
