package com.checkinn.checkinn.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.checkinn.checkinn.Services.ReservationService;

@Controller
@RequestMapping("/reservations")
public class ReservationController {

    private ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }
    
    @GetMapping("/")
    public String getAllReservations() {
        return "reservations";
    }

    @GetMapping("/user")
    public String getReservationsBySelf() {
        return "reservations";
    }

    @GetMapping("/user/{userId}")
    public String getReservationsByUserId() {
        return "reservations";
    }

    @GetMapping("/hotel/{hotelId}")
    public String getReservationsByHotelId() {
        return "reservations";
    }

    @PatchMapping("/edit/{reservationId}")
    public String editReservation() {
        return "reservations";
    }

    @PostMapping("/create/{hotelId}")
    public String createReservation() {
        return "reservations";
    }

    @DeleteMapping("/delete/{reservationId}")
    public String deleteReservation() {
        return "reservations";
    }
}
