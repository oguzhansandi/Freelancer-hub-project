package com.freelance.model.job;

import com.freelance.model.common.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "job_content")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JobContent extends BaseEntity {

    @Column(length = 60)
    @NotNull
    private String title;

    @Column(length = 2500)
    @NotNull
    private String description;

    @Column(length = 500)
    private String requirements;

    @OneToOne
    @JoinColumn(name = "job_posting_id", referencedColumnName = "id")
    private JobPosting jobPosting;

}

