package com.mangjakseon.config;

import com.mangjakseon.security.handler.LoginSuccessHandler;
import com.mangjakseon.security.service.MangjakseonUserDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;



@RequiredArgsConstructor
@Configuration
@Log4j2
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final MangjakseonUserDetailsService userDetailsService;

    // 패스워드 암호화
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 로그인 과정에서 어떤 해쉬로 password를 암호화 했는지 확인
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    // static 설정 무시
    @Override
    public void configure(WebSecurity web) throws Exception{
        web.ignoring().antMatchers("css/**","js/**","assets/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        
        http.formLogin()
                .loginPage("/index")   // 로그인 화면
                .loginProcessingUrl("/login")   // 로그인 처리시 사용되는 페이지
                .defaultSuccessUrl("/");   // 로그인 성공시 보여줄 페이지
        http.csrf().disable();  // csrf 토큰 비활성화

        http.logout()
                .logoutSuccessUrl("/index")
                .invalidateHttpSession(true).deleteCookies("JSESSIONID");

        http.oauth2Login(); // 소셜 로그인 처리
        http.oauth2Login().successHandler(successHandler());
    }

    @Bean
    public LoginSuccessHandler successHandler(){
        return new LoginSuccessHandler();
    }

}
