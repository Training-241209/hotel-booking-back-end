package com.checkinn.checkinn.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class AdminRegisterDTO {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String roleName;
}
