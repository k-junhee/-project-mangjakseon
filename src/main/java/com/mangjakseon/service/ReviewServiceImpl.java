package com.mangjakseon.service;


import com.mangjakseon.dto.ReviewDTO;
import com.mangjakseon.entity.Review;
import com.mangjakseon.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;

    //등록
    @Override
    public Long register(ReviewDTO reviewDTO) {
        Review review = dtoToEntity(reviewDTO);

        reviewRepository.save(review);
        return review.getReviewNum();
    }
    //목록
    @Override
    public List<ReviewDTO> findByMovieIdOrderByReviewNumDesc(String movieId) {
        List<Review> result = reviewRepository.findByMovieIdOrderByReviewNumDesc(movieId);
        return result.stream().map(review -> entityToDTO(review)).collect(Collectors.toList());
    }

    @Override
    public void modify(ReviewDTO reviewDTO) {
        Review review =dtoToEntity(reviewDTO);
        reviewRepository.modifyTitle(review.getReviewTitle(),reviewDTO.getReviewNum());
        reviewRepository.modifyContent(review.getReviewContent(),reviewDTO.getReviewNum());
        reviewRepository.modifyScore(review.getScore(),reviewDTO.getReviewNum());

    }

    @Override
    public void remove(Long reviewNum) {
        reviewRepository.deleteById(reviewNum);

    }
}
