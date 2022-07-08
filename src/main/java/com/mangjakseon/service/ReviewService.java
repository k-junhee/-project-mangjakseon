package com.mangjakseon.service;

import com.mangjakseon.dto.ReviewDTO;
import com.mangjakseon.entity.Member;
import com.mangjakseon.entity.Review;

import java.util.List;

public interface ReviewService {

    Long register (ReviewDTO reviewDTO);//댓글등록

    List<ReviewDTO> findByMovieIdOrderByReviewNumDesc(String movieId);//댓글 목록

    void modify(ReviewDTO reviewDTO);//수정

    void remove(Long reviewNum);//삭제



    default Review dtoToEntity(ReviewDTO reviewDTO){
        Review review = Review.builder()
                .reviewNum(reviewDTO.getReviewNum())
                .reviewTitle(reviewDTO.getReviewTitle())
                .reviewContent(reviewDTO.getReviewContent())
                .likeCount(reviewDTO.getLikeCount())
                .score(reviewDTO.getScore())
                .movieId(reviewDTO.getMovieId())
                .member(Member.builder().memberId(reviewDTO.getMemberId()).build())
                .build();
        return review;
    }
    default ReviewDTO entityToDTO(Review review){
        ReviewDTO reviewdto = ReviewDTO.builder()
                .reviewNum(review.getReviewNum())
                .reviewTitle(review.getReviewTitle())
                .reviewContent(review.getReviewContent())
                .likeCount(review.getLikeCount())
                .score(review.getScore())
                .movieId(review.getMovieId())
                .memberId(review.getMember().getMemberId())
                .nickName(review.getMember().getNickname())
                .profileImage(review.getMember().getProfileImage())
                .regDate(review.getRegDate())
                .modDate(review.getModDate())
                .build();
        return reviewdto;

    }
}
