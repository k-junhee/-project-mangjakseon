package com.mangjakseon.repository;

import com.mangjakseon.entity.Heart;
import org.springframework.data.jpa.repository.JpaRepository;


public interface HeartRepository extends JpaRepository<Heart, Long> {

//    @Query(value = "SELECT COUNT(id) from heart WHERE review_num = :reviewNum", nativeQuery=true)
//    int howManyHeart(@Param("reviewNum") Long reviewNum);

//    Optional<Heart> findHeartByMemberAndReviewNum(Member member, String reviewNum);
}