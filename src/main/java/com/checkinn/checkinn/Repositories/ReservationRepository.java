package com.checkinn.checkinn.Repositories;

import org.springframework.data.repository.CrudRepository;

import com.checkinn.checkinn.Entities.Reservation;

public interface ReservationRepository extends CrudRepository<Reservation, Integer> {

    Iterable<Reservation> findByUser_UserId(int userId);

    Iterable<Reservation> findByHotel_HotelId(int hotelId);
}
