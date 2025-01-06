package com.revature.bdong_ers.DTOs;

import com.revature.bdong_ers.Entities.User;
import com.revature.bdong_ers.Services.RoleService;
import com.revature.bdong_ers.Services.UserService;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserIdPermissionsDTO {

    private int userId;
    private boolean isAdmin;

    public UserIdPermissionsDTO(User user) {
        this.userId = user.getUserId();
        this.isAdmin = false;
    }

    public UserIdPermissionsDTO(User user, RoleService roleService) {
        this.userId = user.getUserId();
        this.isAdmin = roleService.hasAdminPermissions(user);
    }
}
