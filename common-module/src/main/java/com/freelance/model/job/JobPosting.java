package com.freelance.model.job;

import com.freelance.model.common.BaseEntity;
import com.freelance.model.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "job_posting")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JobPosting extends BaseEntity {

    @ManyToOne
    private User employer;

    @ManyToOne
    private Category category;

    @ManyToOne
    private SubCategory subCategory;

    @ManyToOne
    private ServiceType serviceType;

    @Column(name = "application_counts")
    private Integer applicationCounts;

    @OneToOne(mappedBy = "jobPosting", cascade = CascadeType.ALL)
    private JobContent jobContent;

    @OneToMany(mappedBy = "jobPosting", cascade = CascadeType.ALL)
    private List<ServicePackage> packages;


}
