package com.checkinn.checkinn.Services;

import java.util.Optional;

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
        return this.reviewRepository.findByHotel_HotelId(hotelId);
    }

    public Iterable<Review> getReviewsByUserId(int userId) {
        return this.reviewRepository.findByUser_UserId(userId);
    }

    public String createReview(User user, Review review) {
        if (review.getDescription().length()>0 && review.getDescription().length()>0 && review.getRating()>0 && review.getRating()<=5) {
            review.setUser(user);
            return "REVIEW CREATED";
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "INVALID REVIEW INFORMATION");
    }

    public String deleteReview(int userId, int reviewId) {
        if (reviewRepository.existsById(reviewId)) {
            if (reviewRepository.findById(reviewId).get().getUser().getUserId() != userId) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "UNAUTHORIZED");
            }
            reviewRepository.deleteById(reviewId);
            return "REVIEW DELETED";
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "HOTEL NOT FOUND");
    }

    // could have same issue as hotel update if change is needed message me to fix
    public String editReview(int userId, int reviewId, Review review) {
        Optional<Review> resp = reviewRepository.findById(reviewId);
        if (resp.isPresent()){
            Review r = resp.get();
            if (r.getUser().getUserId() != userId) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "UNAUTHORIZED");
            }
            r.setDescription(review.getDescription());
            r.setRating(review.getRating());
            r.setTitle(review.getTitle());
            reviewRepository.save(r);
            return "REVIEW UPDATED";
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "REVIEW NOT FOUND");
        
    }

}
