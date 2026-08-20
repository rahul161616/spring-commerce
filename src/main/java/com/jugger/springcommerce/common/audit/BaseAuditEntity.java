package com.jugger.springcommerce.common.audit;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.Instant;

@Getter
@Setter
@MappedSuperclass
public class BaseAuditEntity {
    private static final String SYSTEM_AUDITOR = "system";

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private Instant updatedAt;

    @CreatedBy
    @Column(name = "created_by", nullable = false, updatable = false, length = 100)
    private String createdBy;

    @LastModifiedBy
    @Column(name = "updated_by", length = 100)
    private String updatedBy;

    @PrePersist
    protected void onCreate() {
        Instant now = Instant.now();
        if (createdAt == null) {
            createdAt = now;
        }
        if (updatedAt == null) {
            updatedAt = null;
        }
        if (createdBy == null || createdBy.isBlank()) {
            createdBy = SYSTEM_AUDITOR;
        }
        if (updatedBy == null || updatedBy.isBlank()) {
            updatedBy = null;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = Instant.now();
        if (updatedBy == null || updatedBy.isBlank()) {
            updatedBy = SYSTEM_AUDITOR;
        }
    }
}
