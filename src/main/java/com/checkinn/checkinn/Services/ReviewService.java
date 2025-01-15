package com.checkinn.checkinn.Services;

import java.util.Optional;

import com.checkinn.checkinn.Entities.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.checkinn.checkinn.Entities.Review;
import com.checkinn.checkinn.Entities.User;
import com.checkinn.checkinn.Repositories.HotelRepository;
import com.checkinn.checkinn.Repositories.ReviewRepository;

@Service
public class ReviewService {

    private ReviewRepository reviewRepository;

    private HotelRepository hotelRepository;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository, HotelRepository hotelRepository) {
        this.reviewRepository = reviewRepository;
        this.hotelRepository = hotelRepository;
    }

    public Iterable<Review> getAllReviews() {
        return this.reviewRepository.findAll();
    }

    public Iterable<Review> getReviewsByHotelId(int hotelId) {
        return this.reviewRepository.findByHotel_HotelId(hotelId);
    }

    public Iterable<Review> getReviewsByUserId(int userId) {
        return this.reviewRepository.findByUser_UserId(userId);
    }

    public User getUserByReviewId(int reviewId) {
        Review review = reviewRepository.findById(reviewId).orElse(null);
        if (review == null) { return null; }
        return review.getUser();
    }

    public String createReview(User user, Review review) {
        if (!review.getTitle().isBlank() && !review.getDescription().isBlank() && review.getRating()>=1 && review.getRating()<=5 && this.hotelRepository.existsById(review.getHotel().getHotelId())) {
            review.setUser(user);
            reviewRepository.save(review);
            return "REVIEW CREATED";
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "INVALID REVIEW INFORMATION");
    }

    public String deleteReview(int reviewId) {
        reviewRepository.deleteById(reviewId);
        return "REVIEW DELETED";
    }

    public String editReview(int userId, int reviewId, Review review) {
        Optional<Review> resp = reviewRepository.findById(reviewId);
        if (resp.isPresent()){
            Review r = resp.get();
            if (r.getUser().getUserId() != userId) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "UNAUTHORIZED");
            }
            if (review.getDescription().isBlank()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "INVALID REVIEW INFORMATION");
            if (review.getRating()<1 || review.getRating()>5) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "INVALID REVIEW INFORMATION");
            if (review.getTitle().isBlank()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "INVALID REVIEW INFORMATION");
            r.setTitle(review.getTitle());
            r.setDescription(review.getDescription());
            r.setRating(review.getRating());
            reviewRepository.save(r);
            return "REVIEW UPDATED";
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "REVIEW NOT FOUND");
        
    }

}
