package com.checkinn.checkinn.Services;

import com.checkinn.checkinn.Entities.User;
import com.checkinn.checkinn.Repositories.UserRepository;

import java.util.Date;

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

    private UserRepository userRepository;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository, HotelRepository hotelRepository, UserRepository userRepository) {
        this.reservationRepository = reservationRepository;
        this.hotelRepository = hotelRepository;
        this.userRepository = userRepository;
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
    public String editReservation(int userId, int reservationId, Reservation newReservation) {
        Reservation r = reservationRepository.findById(reservationId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "RESERVATION NOT FOUND"));
        if (r.getUser().getUserId() != userId) throw new ResponseStatusException(HttpStatus.FORBIDDEN, "UNAUTHORIZED");
        if (!dateValidation(newReservation.getCheckInTime(), newReservation.getCheckOutTime())) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "INVALID DATES");

        r.setCheckInTime(newReservation.getCheckInTime());
        r.setCheckOutTime(newReservation.getCheckOutTime());
        reservationRepository.save(r);
        return "RESERVATION UPDATED";
    }

    public String deleteReservation(int userId, int reservationId) {
        Reservation r = reservationRepository.findById(reservationId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "RESERVATION NOT FOUND"));
        if (r.getUser().getUserId() != userId) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "UNAUTHORIZED");
        }
        reservationRepository.deleteById(reservationId);
        return "RESERVATION DELETED";
    }

    public String createReservation(int userId, int hotelId, Reservation reservation) {
            if (!this.hotelRepository.existsById(hotelId)) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "HOTEL NOT FOUND");
            if (!dateValidation(reservation.getCheckInTime(), reservation.getCheckOutTime())) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "INVALID DATES");
            if (!(this.hotelRepository.findById(hotelId).get().getRooms() > reservationRepository.findByHotel_HotelId(hotelId).size())) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "NO EMPTY ROOMS");
            reservation.setHotel(this.hotelRepository.findById(hotelId).get());
            reservation.setUser(this.userRepository.findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "USER NOT FOUND")));
            reservationRepository.save(reservation);
            return "RESERVATION CREATED";
    }

    public Reservation getReservationById(int reservationId) {
        return reservationRepository.findById(reservationId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "RESERVATION NOT FOUND"));
    }

    public User getUserByReservationId(int reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId).orElse(null);
        if (reservation == null) { return null; }
        return reservation.getUser();
    }

    private boolean dateValidation(Date checkInTime, Date checkOutTime) {
        String inTime = checkInTime.toString();
        String outTime = checkOutTime.toString();
        if (inTime.substring(4,7).equalsIgnoreCase(outTime.substring(4,7))) {
            if(Integer.parseInt(inTime.substring(8, 10))<Integer.parseInt(outTime.substring(8, 10))){
                return true;
            }
        }
        else if (checkInTime.before(checkOutTime)) {
            return true;
        }
        return false;
    }
}
