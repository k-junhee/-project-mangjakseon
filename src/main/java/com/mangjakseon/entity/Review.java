package com.mangjakseon.entity;

import com.mangjakseon.utils.BaseEntity;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = "member")
public class Review extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewNum;         //리뷰 식별번호

    @Column(length = 50)
    private String reviewTitle;     //리뷰 제목

    @Column(length = 5000)
    private String reviewContent;   //리뷰 내용

    @Column
    @ColumnDefault("0")
    private Long likeCount;       //좋아요 개수

    @Column
    private double score;           //별점

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;          //회원(fk)

    @Column
    private String movieId;         //영화 JSON 불러올 pk
    }


