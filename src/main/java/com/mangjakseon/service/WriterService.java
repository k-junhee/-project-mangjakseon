package com.mangjakseon.service;

import com.mangjakseon.dto.WriterDTO;
import com.mangjakseon.entity.Member;

public interface WriterService {

    WriterDTO getWriter(String email);

    default WriterDTO writerEntityToDto(Member member) {
        WriterDTO writerDTO = WriterDTO.builder()
                .memberId(member.getMemberId())
                .nickname(member.getNickname())
                .profileImage(member.getProfileImage())
                .build();

        return writerDTO;
    }
}
