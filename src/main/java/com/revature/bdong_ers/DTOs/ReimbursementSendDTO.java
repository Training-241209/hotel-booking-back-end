package com.revature.bdong_ers.DTOs;

import com.revature.bdong_ers.Entities.Reimbursement;
import com.revature.bdong_ers.Entities.User;
import com.revature.bdong_ers.Services.UserService;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReimbursementSendDTO {
    
    private int reimbursementId;
    private int amount;
    private String description;
    private String status;
    private String firstName;
    private String lastName;
    private String username;

    public ReimbursementSendDTO(Reimbursement reimbursement, UserService userService) {
        int userId = reimbursement.getUserId();
        User user = userService.getUser(userId);

        this.reimbursementId = reimbursement.getReimbursementId();
        this.amount = reimbursement.getAmount();
        this.description = reimbursement.getDescription();
        this.status = reimbursement.getStatus();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.username = user.getUsername();
    }
    
}
