package com.checkinn.checkinn.Entities;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @SequenceGenerator(name = "user_seq",sequenceName = "user_seq",allocationSize = 1)
    @GeneratedValue(generator ="user_seq",strategy = GenerationType.SEQUENCE)
    @Column(name = "user_id", unique = true)
    private int userId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;
}
