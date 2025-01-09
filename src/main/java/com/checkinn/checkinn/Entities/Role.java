package com.checkinn.checkinn.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Role {

    @Id
    @GeneratedValue
    @Column(name = "role_id", unique = true)
    private int roleId;

    @Column(name = "role_name")
    private String roleName;

    @Column(name = "is_admin")
    private boolean isAdmin;
}
