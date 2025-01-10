package com.checkinn.checkinn.Repositories;

import org.springframework.data.repository.CrudRepository;

import com.checkinn.checkinn.Entities.Review;

public interface ReviewRepository extends CrudRepository<Review, Integer> {

    Iterable<Review> findByHotelId(int hotelId);
}
