package com.revature.bdong_ers.DTOs;

import com.revature.bdong_ers.Entities.User;
import com.revature.bdong_ers.Services.RoleService;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserResponseDTO {
    
    private int userId;
    private String username;
    private String firstName;
    private String lastName;
    private String roleName;
    private boolean isAdmin;

    public UserResponseDTO(User user, RoleService roleService) {
        this.userId = user.getUserId();
        this.username = user.getUsername();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.roleName = roleService.getRoleName(user.getRoleId());
        this.isAdmin = roleService.hasAdminPermissions(user);
    }
}
