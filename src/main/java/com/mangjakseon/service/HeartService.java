package com.mangjakseon.service;

import com.mangjakseon.dto.HeartDTO;
import com.mangjakseon.entity.Heart;
import com.mangjakseon.repository.HeartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class HeartService {

    private final HeartRepository heartRepository;

    public void heart(HeartDTO heartDto) {
        Heart heart = Heart.builder()
                .reviewNum(heartDto.getReviewNum())
                .memberId(heartDto.getMemberId())
                .build();
        heartRepository.save(heart);
    }

//    public void unHeart(HeartDTO heartDto) {
//        Optional<Heart> heartOpt = findHeartWithMemberAndReviewNum(heartDto);
//
//        heartRepository.delete(heartOpt.get());
//    }
//
//    public Optional<Heart> findHeartWithMemberAndReviewNum(HeartDTO heartDto) {
//        Optional<Member> userOpt = memberRepository.findByMember(heartDto.getMemberId());
//
//        return heartRepository.findHeartByMemberAndReviewNum(userOpt.get(), heartDto.getReviewNum());
//    }


//    public Long countHeart(String reviewNum) {
//        heartRepository.howManyHeart(reviewNum);
//        return "";
//    }
}




