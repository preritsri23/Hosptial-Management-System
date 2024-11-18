package com.clinicmgmtsystem.config;

import java.io.IOException;
import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        
        // Retrieve the roles from the authentication object
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        // Determine target URL based on role
        String targetUrl = determineTargetUrl(authorities);

        // Redirect to the target URL
        response.sendRedirect(targetUrl);
    }

    protected String determineTargetUrl(Collection<? extends GrantedAuthority> authorities) {
        for (GrantedAuthority authority : authorities) {
            switch (authority.getAuthority()) {
                case "ROLE_ADMIN":
                    return "/admin/home";
                case "ROLE_DOCTOR":
                    return "/doctor/home";
                case "ROLE_PATHOLOGIST":
                    return "/pathologist/home";
                case "ROLE_PHARMACIST":
                    return "/pharmacist/home";
                case "ROLE_PATIENT":
                    return "/patient/home";
            }
        }

        // Fallback if no recognized role is found
        throw new IllegalStateException("User role not found");
    }
}
