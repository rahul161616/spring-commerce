package com.jugger.springcommerce.modules.user.model;

import com.jugger.springcommerce.common.audit.BaseAuditEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table( name = "roles",
        indexes = {
                @Index(name = "idx_roles_name", columnList = "name")
        })
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Role extends BaseAuditEntity {
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE,generator="roles_seq_gen")
    @SequenceGenerator(name="roles_seq_gen",sequenceName="roles_id_seq",allocationSize=1)
    private Long id;
    private String name;
    private String description;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "role_permissions",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id"),
            uniqueConstraints = @UniqueConstraint(
                    name = "uk_role_permissions_role_permission",
                    columnNames = {"role_id", "permission_id"}
            )
    )
    private Set<Permissions> permissions = new HashSet<>();
    private String createdBy;
    private String updatedBy;
    private Instant createdAt;
    private Instant cpdatedAt;
}
