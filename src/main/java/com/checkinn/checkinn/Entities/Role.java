package com.checkinn.checkinn.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Role {

    @Id
    @SequenceGenerator(name = "user_seq",sequenceName = "user_seq",allocationSize = 1)
    @GeneratedValue(generator ="user_seq",strategy = GenerationType.SEQUENCE)
    @Column(name = "role_id", unique = true)
    private int roleId;

    @Column(name = "role_name")
    private String roleName;

    @Column(name = "is_admin")
    private boolean isAdmin;
}
