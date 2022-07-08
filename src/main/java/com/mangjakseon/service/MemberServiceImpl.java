package com.mangjakseon.service;

import com.mangjakseon.dto.MemberDTO;
import com.mangjakseon.entity.Member;
import com.mangjakseon.entity.MemberRole;
import com.mangjakseon.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
@Log4j2
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;

    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Value("${profileImage.path}")  //application.properties에 파일을 저장할 폴더 설정
    private String uploadFolder;

    @Override
    public String register(MemberDTO memberDTO) {
        Member member = dtoToEntity(memberDTO);
        memberRepository.save(member);
        return member.getMemberId();
    }

    @Override
    public MemberDTO read(String memberId) {
        Optional<Member> result = memberRepository.findById(memberId);

        return result.isPresent() ? entityToDto(result.get()) : null;
    }

    @Override
    public void modify(MemberDTO memberDTO, MultipartFile multipartFile) {
        String memberEmail = memberDTO.getEmail();
        boolean isSocial = memberDTO.isFromSocial();
        Optional<Member> result = memberRepository.findByEmail(memberEmail,isSocial);

        //Member 엔티티에 view에서 받은 회원 고유 ID와 일치하는 Member를 찾아 저장
        Member member = memberRepository.getReferenceById(memberDTO.getMemberId());
        //profile image 파일의 이름은 이메일_원본파일명 으로 저장
        String imageFileName = member.getEmail() + "_" + multipartFile.getOriginalFilename();
        //이미지파일의 경로 설정
        Path imageFilePath = Paths.get(uploadFolder+imageFileName);

        if (multipartFile.getSize() != 0) {     //파일이 업로드 되었는지 확인하여
            try {
                if (member.getProfileImage() != null) {     //이미 프로필 사진이 있는 경우 기존의 프로필사진을 지우고
                    File file = new File(uploadFolder + member.getProfileImage());
                    file.delete();
                }
                Files.write(imageFilePath, multipartFile.getBytes());   //프로필 사진을 새로 등록
            } catch (Exception e) {
                e.printStackTrace();
            }
            member.setProfileImage(imageFileName);  //Member에 프로필 이미지 저장
        }

        if (result.isPresent() && isSocial){
            log.info("== MOD SOCIAL ==");
            Member entity = result.get();
            if (!(memberDTO.getNickname().equals(""))) {
                entity.modifyNickname(memberDTO.getNickname());
            }
            memberRepository.save(entity);
        } else if (result.isPresent()) {

            log.info("== MOD USER ==");
            Member entity = result.get();
            if (!(memberDTO.getPassword().equals(""))) {
                entity.modifyPassword(passwordEncoder.encode(memberDTO.getPassword()));
            }
            if (!(memberDTO.getNickname().equals(""))) {
                entity.modifyNickname(memberDTO.getNickname());
            }
            memberRepository.save(entity);
        }
    }

    @Override
    public void remove(String memberId) {
        memberRepository.deleteById(memberId);
    }

    // 회원가입시 유효성 체크
    @Transactional(readOnly = true)
    public Map<String,String> validateHandling(Errors errors) {
        Map<String,String> validatorResult = new HashMap<>();

        // 유효성 검사에 실패한 필드 목록 수신
        for (FieldError error : errors.getFieldErrors()) {
            String validKey = String.format("valid_%s",error.getField());
            validatorResult.put(validKey,error.getDefaultMessage());
        }
        return validatorResult;
    }

    // 회원가입시 이메일 중복 여부 확인
    @Transactional(readOnly = true)
    public void checkEmailDuplication(MemberDTO memberDTO) {
        boolean Duplicate = memberRepository.existsByEmail(memberDTO.getEmail());
        if (Duplicate) {
            throw new IllegalStateException("이미 사용중인 이메일입니다.");
        }
    }

    // 회원가입시 닉네임 중복 여부 확인
    @Transactional(readOnly = true)
    public void checkNicknameDuplication(MemberDTO memberDTO) {
        boolean Duplicate = memberRepository.existsByNickname(memberDTO.getEmail());
        if (Duplicate) {
            throw new IllegalStateException("이미 사용중인 닉네임입니다.");
        }
    }

    // 회원정보 수정시 닉네임 중복 여부 확인
    public int nicknameCheck(String nickname){
        return memberRepository.countByNickname(nickname);
    }


    public boolean accountCheck(String email, String password) {
        String pass = memberRepository.findByPassword(email);
        log.info("REF CHK ::: "+passwordEncoder.matches(password,pass));
        return passwordEncoder.matches(password,pass);
    }
}
