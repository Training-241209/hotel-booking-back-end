package com.checkinn.checkinn.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PasswordDTO {

    private String oldPassword;
    private String newPassword;
}
