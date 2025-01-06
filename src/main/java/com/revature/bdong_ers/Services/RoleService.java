package com.revature.bdong_ers.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.bdong_ers.Entities.Role;
import com.revature.bdong_ers.Entities.User;
import com.revature.bdong_ers.Repositories.RoleRepository;
import com.revature.bdong_ers.Repositories.UserRepository;
@Service
public class RoleService {

    private RoleRepository roleRepository;
    private UserRepository userRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository, UserRepository userRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    public String getRoleName(int roleId) {
        Role role = roleRepository.findByRoleId(roleId).orElse(null);
        return role.getName();
    }
    
    public boolean hasAdminPermissions(int userId) {
        User user = userRepository.findByUserId(userId).orElse(null);
        Role role = roleRepository.findById(user.getRoleId()).orElse(null);
        if (role == null) {
            return false;
        }
        return role.isAdmin();
    }
    
    public boolean hasAdminPermissions(User user) {
        Role userRole = roleRepository.findById(user.getRoleId()).orElse(null);
        if (userRole == null) {
            return false;
        }
        return userRole.isAdmin();
    }

    public User promoteUser(int userId) {
        User user = userRepository.findByUserId(userId).orElse(null);
        // I don't like this implementation. With more roles + specificity, I would make a better implementation.
        // However, there are two roles, and adding to roleRepository to search for a role with admin ID for this
        // also feels like a waste of time. Hard-coding it is.
        int adminRoleId = 2;
        user.setRoleId(adminRoleId);
        return userRepository.save(user);
    }
}
