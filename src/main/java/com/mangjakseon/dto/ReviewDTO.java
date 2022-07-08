package com.mangjakseon.dto;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReviewDTO {
    private Long reviewNum;     //리뷰 식별번호

    private String reviewTitle;     //리뷰 제목

    private String reviewContent;       //리뷰 내용

    private Long likeCount;         //좋아요 체크

    private double score;           //별점

    private String movieId;         //영화pk  (어떤 영화인지)

    private String memberId;        //회원pk (누가 썼는지)
    private String nickName;        //회원이름
    private String profileImage;
    private LocalDateTime regDate, modDate;
}
