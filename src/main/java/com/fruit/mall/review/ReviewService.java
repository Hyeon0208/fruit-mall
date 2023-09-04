package com.fruit.mall.review;

import com.fruit.mall.review.dto.ReviewSaveReqDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;

    public void insertReview(ReviewSaveReqDto reviewSaveReqDto) {
        Review review = reviewSaveReqDto.toEntity(reviewSaveReqDto);
        reviewRepository.insertReview(review);
    }
}