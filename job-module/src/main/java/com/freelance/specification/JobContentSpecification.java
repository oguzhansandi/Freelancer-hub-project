package com.freelance.specification;

import com.freelance.model.job.JobContent;
import org.springframework.data.jpa.domain.Specification;

public class JobContentSpecification {

    public static Specification<JobContent> hasCategory(Long categoryId) {
        return (root, query, cb) -> categoryId == null ? null :
                cb.equal(root.get("jobPosting").get("category").get("id"), categoryId);
    }

    public static Specification<JobContent> hasSubCategory(Long subCategoryId) {
        return (root, query, cb) -> subCategoryId == null ? null :
                cb.equal(root.get("jobPosting").get("subCategory").get("id"), subCategoryId);
    }

    public static Specification<JobContent> hasServiceType(Long serviceTypeId) {
        return (root, query, cb) -> serviceTypeId == null ? null :
                cb.equal(root.get("jobPosting").get("serviceType").get("id"), serviceTypeId);
    }
}
