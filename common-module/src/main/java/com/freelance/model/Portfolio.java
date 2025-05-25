package com.freelance.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "portfolio")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Portfolio extends BaseEntity{

    @OneToOne
    private User user;

    @NotNull
    private String name;

    @NotNull
    private String category;

    @NotNull
    private String description;

    @NotNull
    private int likeCount;

    @NotNull
    private int viewCount;

    @NotNull
    @Column(length = 1000)
    private String mediaURL;

}
