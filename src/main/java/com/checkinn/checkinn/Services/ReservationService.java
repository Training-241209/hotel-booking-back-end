package com.checkinn.checkinn.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.checkinn.checkinn.Entities.Reservation;
import com.checkinn.checkinn.Repositories.ReservationRepository;

@Service
public class ReservationService {

    ReservationRepository reservationRepository;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public Iterable<Reservation> getAllReservations() {
        return this.reservationRepository.findAll();
    }

    public Iterable<Reservation> getReservationsByUserId(int userId) {
        return this.reservationRepository.findByUser_UserId(userId);
    }

    public Iterable<Reservation> getReservationsByHotelId(int hotelId) {
        return this.reservationRepository.findByHotel_HotelId(hotelId);
    }

    public String editReservation(int userId, int reservationId, Reservation reservation) {
        Optional<Reservation> reservationToEdit = this.reservationRepository.findById(reservationId);
        if (reservationToEdit)
        return "RESERVATION UPDATED";
    }
}
