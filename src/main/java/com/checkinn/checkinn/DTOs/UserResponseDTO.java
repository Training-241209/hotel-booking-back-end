package com.checkinn.checkinn.DTOs;

import com.checkinn.checkinn.Entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserResponseDTO {

    private int userId;
    private String firstName;
    private String lastName;
    private String email;

    public UserResponseDTO(User user) {
        this.userId = user.getUserId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
    }
}
