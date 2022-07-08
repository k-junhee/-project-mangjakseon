package com.mangjakseon.security.handler;

import com.mangjakseon.security.dto.MangjakseonAuthMemberDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Log4j2
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        MangjakseonAuthMemberDTO mangjakseonAuthMember = (MangjakseonAuthMemberDTO)authentication.getPrincipal();

        boolean fromSocial = mangjakseonAuthMember.isFromSocial();

        if (fromSocial){
            redirectStrategy.sendRedirect(request, response, "/home");
        }

    }
}
