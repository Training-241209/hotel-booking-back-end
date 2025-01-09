package com.checkinn.checkinn.Controllers;

import com.checkinn.checkinn.Services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/role")
public class RoleController {

    private RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService){
        this.roleService = roleService;
    }
}
