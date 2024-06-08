package org.vm93.cinemille.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.vm93.cinemille.payload.LoginDTO;
import org.vm93.cinemille.security.CustomUserDetailService;
import org.vm93.cinemille.security.JwtProvider;

@Service
public class AuthService {

    private AuthenticationManager authenticationManager;
    private JwtProvider jwtTokenProvider;
    private CustomUserDetailService custUserDetailsService;

    public AuthService(AuthenticationManager authenticationManager,
            JwtProvider jwtTokenProvider, CustomUserDetailService custUserDetailsService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.custUserDetailsService = custUserDetailsService;
    }

    public String login(LoginDTO loginDto) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getUsername(), loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtTokenProvider.generateToken(authentication);

        return token;
    }

    public String refreshToken(String oldToken) {
        if (!jwtTokenProvider.validateToken(oldToken)) {
            throw new RuntimeException("Invalid JWT token");
        }

        String username = jwtTokenProvider.getUsername(oldToken);
        UserDetails userDetails = custUserDetailsService.loadUserByUsername(username);

        // Create a new authentication token
        UsernamePasswordAuthenticationToken newAuthToken = new UsernamePasswordAuthenticationToken(userDetails, null,
                userDetails.getAuthorities());

        // Set the authentication in the security context
        SecurityContextHolder.getContext().setAuthentication(newAuthToken);

        // Generate new JWT token
        String newToken = jwtTokenProvider.generateToken(newAuthToken);

        return newToken;
    }

}