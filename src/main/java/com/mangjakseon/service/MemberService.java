package com.mangjakseon.service;

import com.mangjakseon.dto.MemberDTO;
import com.mangjakseon.entity.Member;
import com.mangjakseon.entity.MemberRole;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.Errors;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface MemberService {

    String register(MemberDTO memberDTO);

    MemberDTO read(String memberId);

    void modify(MemberDTO memberDTO, MultipartFile multipartFile);

    void remove(String memberId);

    default Member dtoToEntity(MemberDTO memberDTO) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        Member member = Member.builder()
                .email(memberDTO.getEmail())
                .password(passwordEncoder.encode(memberDTO.getPassword()))
                .nickname(memberDTO.getNickname())
                .fromSocial(false)
                .profileImage(memberDTO.getProfileImage())
                .movieViewed(false)
                .movieId(memberDTO.getMovieId())
                .build();

        member.addMemberRole(MemberRole.USER);
        return member;
    }

    default MemberDTO entityToDto(Member member){
        MemberDTO memberDTO = MemberDTO.builder()
                .memberId(member.getMemberId())
                .email(member.getEmail())
                .password(member.getPassword())
                .nickname(member.getNickname())
                .fromSocial(member.isFromSocial())
                .profileImage(member.getProfileImage())
                .movieViewed(member.isMovieViewed())
                .movieId(member.getMovieId())
                .role(member.getRoleSet())
                .regDate(member.getRegDate())
                .modDate(member.getModDate())
                .build();

        return memberDTO;
    }

    // 회원가입시 유효성 체크
    Map<String,String> validateHandling(Errors errors);

    // 회원가입시 이메일 중복 여부 확인
    void checkEmailDuplication(MemberDTO memberDTO);
    // 회원가입시 닉네임 중복 여부 확인
    void checkNicknameDuplication(MemberDTO memberDTO);
    // 회원정보 수정시 닉네임 중복 여부 확인
    int nicknameCheck(String nickname);

    boolean accountCheck(String email, String password);
}
