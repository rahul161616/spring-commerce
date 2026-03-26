package com.jugger.springcommerce.modules.user.model;

import com.jugger.springcommerce.common.audit.BaseAuditEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(  name = "permissions",
        indexes = {
                @Index(name = "idx_permissions_name", columnList = "name")
        })
public class Permissions extends BaseAuditEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE,generator="permissions_seq_gen")
    @SequenceGenerator(name="permissions_seq_gen",sequenceName = "permissions_seq_id",allocationSize = 1)
    private Long id;
    private String name;
    private String description;
    private String createdBy;
    private String updatedBy;
    private Instant createdAt;
    private Instant updatedAt;
}
