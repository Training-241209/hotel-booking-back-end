package com.revature.bdong_ers.Controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.revature.bdong_ers.DTOs.ReimbursementSendDTO;
import com.revature.bdong_ers.DTOs.ReimbursementStatusDTO;
import com.revature.bdong_ers.Entities.Reimbursement;
import com.revature.bdong_ers.Services.AuthService;
import com.revature.bdong_ers.Services.ReimbursementService;
import com.revature.bdong_ers.Services.UserService;

@RestController
public class ReimbursementController {
    
    private ReimbursementService reimbursementService;
    private AuthService authService;
    private UserService userService;

    @Autowired
    public ReimbursementController(ReimbursementService reimbursementService, AuthService authService, UserService userService) {
        this.reimbursementService = reimbursementService;
        this.authService = authService;
        this.userService = userService;
    }

    @PostMapping(value="/reimbursements")
    public ResponseEntity<Reimbursement> postReimbursement(@RequestHeader("Authorization") String token,
            @RequestBody Reimbursement reimbursement) {
        
        // Check if reimbursement creator does not match token user
        if (!authService.userMatches(token, reimbursement.getUserId())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        return ResponseEntity.ok().body(reimbursementService.createReimbursement(reimbursement));
    }

    @GetMapping(value="/reimbursements")
    public ResponseEntity<List<ReimbursementSendDTO>> getAllReimbursements(@RequestHeader("Authorization") String token) {

        // Check if user is not an admin
        if (!authService.hasAdminPermissions(token)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }

        return ResponseEntity.ok().body(reimbursementService.viewAllReimbursements()
            .stream()
            .map((Reimbursement rb) -> new ReimbursementSendDTO(rb, userService))
            .collect(Collectors.toList()));
    }

    @GetMapping(value="/reimbursements/me")
    public ResponseEntity<List<ReimbursementSendDTO>> getReimbursementsByUser(@RequestHeader("Authorization") String token) {
        
        if (!authService.userExists(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        return ResponseEntity.ok().body(reimbursementService.viewReimbursementsByUserId(authService.getTokenId(token))
            .stream()
            .map((Reimbursement rb) -> new ReimbursementSendDTO(rb, userService))
            .collect(Collectors.toList()));
    }

    @GetMapping(value="/reimbursements/status/{status}")
    public ResponseEntity<List<ReimbursementSendDTO>> getReimbursementsByStatus(@RequestHeader("Authorization") String token,
            @PathVariable String status) {
        
        // Check if user is not an admin
        if (!authService.hasAdminPermissions(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        return ResponseEntity.ok().body(reimbursementService.viewReimbursementsByStatus(status.toUpperCase())
            .stream()
            .map((Reimbursement rb) -> new ReimbursementSendDTO(rb, userService))
            .collect(Collectors.toList()));
    }

    // @GetMapping(value="/reimbursements/{id}/status/{status}")
    // public ResponseEntity<List<Reimbursement>> getReimbursementsByUserIdAndStatus(@RequestHeader("Authorization") String token,
    //         @PathVariable int id, @PathVariable String status) {
        
    //     // Check if user is not an admin AND if user is not obtaining their own reimbursements
    //     if (!authService.hasAdminPermissionsOrUserMatches(token, id)) {
    //         return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
    //     }
    //     return ResponseEntity.ok().body(reimbursementService.viewReimbursementsByStatus(id, status.toUpperCase()));
    // }

    @PatchMapping(value="/reimbursements/{id}")
    public ResponseEntity<Reimbursement> patchReimbursementById(@RequestHeader("Authorization") String token,
            @PathVariable int id, @RequestBody Reimbursement reimbursement) {
        
        // Token user must match reimbursement being updated
        if (!authService.userMatches(token, reimbursementService.viewReimbursement(id).getUserId())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        // Cannot update reimbursements with a non-pending status
        if (!reimbursementService.viewReimbursement(id).getStatus().equals("PENDING")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }

        // Implementation does not update status
        return ResponseEntity.ok().body(reimbursementService.updateReimbursement(id, reimbursement));
    }

    @PatchMapping(value="/reimbursements")
    public ResponseEntity<Reimbursement> patchReimbursementByIdAndStatus(@RequestHeader("Authorization") String token,
            @RequestBody ReimbursementStatusDTO reimbursementStatusDTO) {

        int reimbursementId = reimbursementStatusDTO.getReimbursementId();

        // Check if user is not an admin. Employees cannot update their own reimbursement statuses.
        if (!authService.hasAdminPermissions(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        // Can't approve your own reimbursements, that's what we call fraud!
        if (authService.getTokenId(token) == reimbursementService.viewReimbursement(reimbursementId).getUserId()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        return ResponseEntity.ok().body(reimbursementService
        .updateReimbursementStatus(reimbursementStatusDTO.getReimbursementId(), reimbursementStatusDTO.getStatus()));
    }

}