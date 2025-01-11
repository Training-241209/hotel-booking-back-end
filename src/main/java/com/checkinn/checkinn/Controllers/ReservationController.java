package com.checkinn.checkinn.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import com.checkinn.checkinn.Constants.GeneralConstants;
import com.checkinn.checkinn.Entities.Reservation;
import com.checkinn.checkinn.Services.AuthService;
import com.checkinn.checkinn.Services.ReservationService;

@Controller
@RequestMapping("/reservations")
public class ReservationController {

    private ReservationService reservationService;

    private AuthService authService;

    @Autowired
    public ReservationController(ReservationService reservationService, AuthService authService) {
        this.reservationService = reservationService;
        this.authService = authService;
    }
    
    @GetMapping("/")
    public ResponseEntity<Iterable<Reservation>> getAllReservations(@RequestHeader (GeneralConstants.AUTH_HEADER_NAME) String token) {
        this.authService.isAdminThrowOtherwise(token);
        return ResponseEntity.ok().body(this.reservationService.getAllReservations());
    }

    @GetMapping("/user")
    public ResponseEntity<Iterable<Reservation>> getReservationsBySelf(@RequestHeader (GeneralConstants.AUTH_HEADER_NAME) String token) {
        this.authService.decodeToken(token);
        return ResponseEntity.ok().body(this.reservationService.getReservationsByUserId(this.authService.decodeToken(token)));
    }

    @GetMapping("/user/{userId}")
    public String getReservationsByUserId(@RequestHeader (GeneralConstants.AUTH_HEADER_NAME) String token) {
        return "reservations";
    }

    @GetMapping("/hotel/{hotelId}")
    public String getReservationsByHotelId(@RequestHeader (GeneralConstants.AUTH_HEADER_NAME) String token) {
        return "reservations";
    }

    @PatchMapping("/edit/{reservationId}")
    public String editReservation(@RequestHeader (GeneralConstants.AUTH_HEADER_NAME) String token) {
        return "reservations";
    }

    @PostMapping("/create/{hotelId}")
    public String createReservation(@RequestHeader (GeneralConstants.AUTH_HEADER_NAME) String token) {
        return "reservations";
    }

    @DeleteMapping("/delete/{reservationId}")
    public String deleteReservation(@RequestHeader (GeneralConstants.AUTH_HEADER_NAME) String token) {
        return "reservations";
    }
}
