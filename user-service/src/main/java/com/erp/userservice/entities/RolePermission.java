package com.erp.userservice.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "role_permission")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RolePermission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;


    @ManyToOne
    @JoinColumn(name = "permission_id")
    private Permission permission;
}
