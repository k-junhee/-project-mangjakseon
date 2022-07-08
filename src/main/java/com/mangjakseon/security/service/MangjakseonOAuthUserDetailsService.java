package com.mangjakseon.security.service;

import com.mangjakseon.entity.Member;
import com.mangjakseon.entity.MemberRole;
import com.mangjakseon.repository.MemberRepository;
import com.mangjakseon.security.dto.MangjakseonAuthMemberDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class MangjakseonOAuthUserDetailsService extends DefaultOAuth2UserService {

    private final MemberRepository repository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException{
        log.info("userRequest: " + userRequest);

        String clientName = userRequest.getClientRegistration().getClientName();
        log.info("clientName: " + clientName);  //사용하는 OAuth2 API 종류 확인, ex)구글의 경우 Google 출력
        log.info(userRequest.getAdditionalParameters());

        OAuth2User oAuth2User = super.loadUser(userRequest);

        log.info("==========================================");
        oAuth2User.getAttributes().forEach((k,v) -> {
            log.info(k + ":" + v);  // 결과값 : sub, picture, email, email_verified 등이 출력
        });

        String email = null;

        if (clientName.equals("Google")){   //구글을 이용하는 경우
            email = oAuth2User.getAttribute("email");  // email 변수에 구글에서 받아오는 email 주소 저장
        }
        log.info("EMAIL: " + email);

        Member member = saveSocialMember(email);

        MangjakseonAuthMemberDTO mangjakseonAuthMember = new MangjakseonAuthMemberDTO(
            member.getEmail(),
            member.getPassword(),
                true,
            member.getRoleSet().stream()
                    .map(role -> new SimpleGrantedAuthority("ROLE_" + role.name()))
                    .collect(Collectors.toList()),
                oAuth2User.getAttributes()
        );
        mangjakseonAuthMember.setNickname(member.getNickname());

        return mangjakseonAuthMember;
    }

    private Member saveSocialMember(String email){

        //소셜 로그인 자동회원가입시 비밀번호를 불규칙 문자열로 생성
        Random random = new Random();
        StringBuilder socialPassword = new StringBuilder(10);
        for(int i=0; i<10; i++){
            char tempPw = (char)('a'+random.nextInt('z'-'a'));
            socialPassword.append(tempPw);
        }

        log.info("SocialPW??? : "+socialPassword);

        //기존에 동일 이메일로 가입한 회원이 있는 경우 조회만 처리
        Optional<Member> result = repository.findByEmail(email,true);

        if (result.isPresent()){
            log.info("Search Social");
            return result.get();
        }

        //기존에 가입한 이메일이 없다면 회원테이블에 추가, 닉네임은 이메일로 설정
        Member member = Member.builder()
                .email(email)
                .password(passwordEncoder.encode(socialPassword))
                .nickname(email)
                .fromSocial(true)
                .build();

        member.addMemberRole(MemberRole.USER);

        log.info("Create Social");
        repository.save(member);

        return member;
    }
}
