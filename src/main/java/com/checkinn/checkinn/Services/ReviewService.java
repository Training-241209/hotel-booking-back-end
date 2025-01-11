package com.checkinn.checkinn.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.checkinn.checkinn.Entities.Review;
import com.checkinn.checkinn.Entities.User;
import com.checkinn.checkinn.Repositories.ReviewRepository;

@Service
public class ReviewService {

    private ReviewRepository reviewRepository;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public Iterable<Review> getAllReviews() {
        return this.reviewRepository.findAll();
    }

    public Iterable<Review> getReviewsByHotelId(int hotelId) {
        return this.reviewRepository.findByHotelId(hotelId);
    }

    public Iterable<Review> getReviewsByUserId(int userId) {
        return this.reviewRepository.findByUserId(userId);
    }

    public String createReview(User user, Review review) {
        if (review.getDescription().length()>0 && review.getDescription().length()>0 && review.getRating()>0 && review.getRating()<=5) {
            review.setUser(user);
            return "REVIEW CREATED";
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "INVALID REVIEW INFORMATION");
    }

}
