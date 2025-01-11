package com.checkinn.checkinn.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.checkinn.checkinn.Entities.Review;
import com.checkinn.checkinn.Services.ReviewService;
import com.checkinn.checkinn.Constants.GeneralConstants;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    private ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping("/")
    public ResponseEntity<Iterable<Review>> getAllReviews() {
        return ResponseEntity.ok().body(this.reviewService.getAllReviews());
    }

    @GetMapping("/hotel/{hotelId}")
    public ResponseEntity<Iterable<Review>> getReviewsByHotelId(@PathVariable int hotelId) {
        return ResponseEntity.ok().body(this.reviewService.getReviewsByHotelId(hotelId));
    }

    @GetMapping("/user/")
    public Iterable<Review> getReviewsBySelf(@RequestHeader (GeneralConstants.AUTH_HEADER_NAME) String token) {
        return null;
    }

    @GetMapping("/user/{userId}")
    public Iterable<Review> getReviewsByUserId(@PathVariable int userId) {
        return null;
    }

    @PatchMapping("/edit/{reviewId}")
    public String editReview(@RequestHeader (GeneralConstants.AUTH_HEADER_NAME) String token, @PathVariable int reviewId, @RequestBody Review review) {
        return null;
    }

    @PostMapping("/create")
    public String createReview(@RequestHeader (GeneralConstants.AUTH_HEADER_NAME) String token, @RequestBody Review review) {
        return null;
    }

    @DeleteMapping("/delete/{reviewId}")
    public String deleteReview(@RequestHeader (GeneralConstants.AUTH_HEADER_NAME) String token, @PathVariable int reviewId) {
        return null;
    }

}
