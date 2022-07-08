package com.mangjakseon.security.validation;

import com.mangjakseon.dto.MemberDTO;
import com.mangjakseon.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@RequiredArgsConstructor
@Component
public class CheckEmailValidator extends AbstractValidator<MemberDTO> {

    private final MemberRepository memberRepository;

    @Override
    protected void doValidate(MemberDTO memberDTO, Errors errors) {
        if (memberRepository.existsByEmail(memberDTO.getEmail())) {
            errors.rejectValue("email","이메일 중복 오류","이미 사용중인 이메일입니다.");
        }
    }
}
