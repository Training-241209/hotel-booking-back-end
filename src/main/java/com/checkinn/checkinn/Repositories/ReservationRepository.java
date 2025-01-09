package com.checkinn.checkinn.Repositories;

import org.springframework.data.repository.CrudRepository;

import com.checkinn.checkinn.Entities.Hotel;

public interface ReservationRepository extends CrudRepository<Review, Integer>{
}
