package com.checkinn.checkinn.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
        return "RESERVATION UPDATED";
    }

    public String deleteReservation(int userId, int reservationId) {
        if (reservationRepository.existsById(reservationId)) {
            if (reservationRepository.findById(reservationId).get().getUser().getUserId() != userId) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "UNAUTHORIZED");
            }
            reservationRepository.deleteById(reservationId);
            return "RESERVATION DELETED";
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "RESERVATION NOT FOUND");
    }

    public String createReservation(int userId, int hotelId, Reservation reservation) {
        return "RESERVATION CREATED";
    }
}
