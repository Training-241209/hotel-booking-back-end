package com.checkinn.checkinn.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {

    private ReviewService reviewService;

    @Autowired
    public ReviewService(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

}
