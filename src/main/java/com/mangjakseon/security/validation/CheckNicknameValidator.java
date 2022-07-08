package com.mangjakseon.security.validation;

import com.mangjakseon.dto.MemberDTO;
import com.mangjakseon.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@RequiredArgsConstructor
@Component
public class CheckNicknameValidator extends AbstractValidator<MemberDTO> {

    private final MemberRepository memberRepository;

    @Override
    protected void doValidate(MemberDTO memberDTO, Errors errors) {
        if (memberRepository.existsByNickname(memberDTO.getNickname())) {
            errors.rejectValue("nickname","닉네임 중복 오류","이미 사용중인 닉네임입니다.");
        }
    }
}
