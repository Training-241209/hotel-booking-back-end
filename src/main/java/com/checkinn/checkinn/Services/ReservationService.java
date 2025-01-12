package com.checkinn.checkinn.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.checkinn.checkinn.Entities.Reservation;
import com.checkinn.checkinn.Repositories.HotelRepository;
import com.checkinn.checkinn.Repositories.ReservationRepository;

@Service
public class ReservationService {

    private ReservationRepository reservationRepository;

    private HotelRepository hotelRepository;

    private UserService userService;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository, HotelRepository hotelRepository, UserService userService) {
        this.reservationRepository = reservationRepository;
        this.hotelRepository = hotelRepository;
        this.userService = userService;
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

    /*
     *  better date validation to be added later
     */
    public String editReservation(int userId, int reservationId, Reservation reservation) {
        if (!reservationRepository.existsById(reservationId)) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "RESERVATION NOT FOUND");
        if (reservationRepository.findById(reservationId).get().getUser().getUserId() != userId) throw new ResponseStatusException(HttpStatus.FORBIDDEN, "UNAUTHORIZED");
        if (reservation.getCheckInTime().after(reservation.getCheckOutTime())) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "INVALID DATES");
        Reservation r = reservationRepository.findById(reservationId).get();
        r.setCheckInTime(reservation.getCheckInTime());
        r.setCheckOutTime(reservation.getCheckOutTime());
        reservationRepository.save(r);
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

    /*
     *  better date validation to be added later
     */
    public String createReservation(int userId, int hotelId, Reservation reservation) {
            if (!this.hotelRepository.existsById(hotelId)) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "HOTEL NOT FOUND");
            if (reservation.getCheckInTime().after(reservation.getCheckOutTime())) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "INVALID DATES");
            if (!(this.hotelRepository.findById(hotelId).get().getRooms() >= reservationRepository.findByHotel_HotelId(hotelId).size())) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "NO EMPTY ROOMS");
            reservation.setHotel(this.hotelRepository.findById(hotelId).get());
            reservation.setUser(this.userService.getUserById(userId));
            reservationRepository.save(reservation);
            return "RESERVATION CREATED";
    }

    public Reservation getReservationById(int reservationId) {
        return reservationRepository.findById(reservationId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "RESERVATION NOT FOUND"));
    }
}
