package com.freelance.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "categories")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Category extends BaseEntity {

    private String name;

    private int freelancers;

    private int jobPosts;
}
