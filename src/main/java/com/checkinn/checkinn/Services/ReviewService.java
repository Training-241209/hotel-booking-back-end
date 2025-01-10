package com.checkinn.checkinn.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.checkinn.checkinn.Entities.Review;
import com.checkinn.checkinn.Repositories.ReviewRepository;

@Service
public class ReviewService {

    private ReviewRepository reviewRepository;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public Iterable<Review> getAllReviews() {
        return this.
    }

}
