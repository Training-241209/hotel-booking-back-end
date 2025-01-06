package com.revature.bdong_ers.Services;

import com.revature.bdong_ers.Entities.Reimbursement;
import com.revature.bdong_ers.Entities.User;
import com.revature.bdong_ers.Repositories.ReimbursementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ReimbursementService {

    private ReimbursementRepository reimbursementRepository;

    @Autowired
    public ReimbursementService(ReimbursementRepository reimbursementRepository) {
        this.reimbursementRepository = reimbursementRepository;
    }

    public Reimbursement createReimbursement(Reimbursement reimbursement) {
        // Amount must be > 0
        if (reimbursement.getAmount() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        if (reimbursement.getStatus() == null) {
            System.out.println("----------------------");
            System.out.println("CREATED WITHOUT STATUS");
            System.out.println("----------------------");
            System.out.println("Setting to PENDING....");
            System.out.println("----------------------");
            reimbursement.setStatus("PENDING");
        }
        return reimbursementRepository.save(reimbursement);
    }

    public Reimbursement viewReimbursement(int reimbursementId) {
        return reimbursementRepository.findById(reimbursementId).orElse(null);
    }
    
    public List<Reimbursement> viewAllReimbursements() {
        return (List<Reimbursement>) reimbursementRepository.findAll();
    }

    public List<Reimbursement> viewReimbursementsByUserId(int id) {
        return (List<Reimbursement>) reimbursementRepository.findByUserId(id).orElse(null);
    }

    public List<Reimbursement> viewReimbursementsByStatus(String status) {
        return reimbursementRepository.findByStatus(status).orElse(null);
    }

    public List<Reimbursement> viewReimbursementsByStatus(User user, String status) {
        return reimbursementRepository.findByUserIdAndStatus(user.getUserId(), status).orElse(null);
    }

    public List<Reimbursement> viewReimbursementsByStatus(int userId, String status) {
        return reimbursementRepository.findByUserIdAndStatus(userId, status).orElse(null);
    }

    public Reimbursement updateReimbursement(int id, Reimbursement reimbursement) {

        // Reimbursement must already exist
        Reimbursement updatedReimbursement = reimbursementRepository.findById(id).
                orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));

        // Amount must be > 0
        if (reimbursement.getAmount() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        // Do not update status, use updateReimbursementStatus instead
        updatedReimbursement.setDescription(reimbursement.getDescription());
        updatedReimbursement.setAmount(reimbursement.getAmount());
        return this.reimbursementRepository.save(updatedReimbursement);
    }

    public Reimbursement updateReimbursementStatus(int id, String status) {

        // Reimbursement must already exist
        Reimbursement updatedReimbursement = reimbursementRepository.findById(id).
                orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));

        // Cannot undo status changes or set reimbursements back to pending
        if (!updatedReimbursement.getStatus().equals("PENDING") || status.equals("PENDING")) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        updatedReimbursement.setStatus(status);
        return this.reimbursementRepository.save(updatedReimbursement);
    }

    public int deleteByUserId(int userId) {
        return reimbursementRepository.deleteByUserId(userId).orElse(null).size();
    }
}
