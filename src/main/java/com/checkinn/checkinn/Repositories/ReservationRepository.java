package com.checkinn.checkinn.Repositories;

import org.springframework.data.repository.CrudRepository;

import com.checkinn.checkinn.Entities.Reservation;

public interface ReservationRepository extends CrudRepository<Reservation, Integer>{
}
