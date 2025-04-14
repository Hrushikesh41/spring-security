package com.example.security.filter;

import com.example.security.service.CustomUserDetailsService;
import com.example.security.service.TokenBlacklistService;
import com.example.security.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private TokenBlacklistService blacklistService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        final String authorizationHeader = request.getHeader("Authorization");
        System.out.println("Request URI: " + request.getRequestURI());
        System.out.println("Authorization header: " + authorizationHeader);

        String username = null;
        String jwt = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
            System.out.println("JWT: " + jwt);
            try {
                username = jwtUtil.extractUsername(jwt);
                System.out.println("Extracted username: " + username);
                System.out.println("Extracted role: " + jwtUtil.extractRole(jwt));
            } catch (Exception e) {
                System.out.println("JWT parsing error: " + e.getMessage());
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Invalid JWT: " + e.getMessage());
                return;
            }
        } else {
            System.out.println("No valid Bearer token found in header");
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
            System.out.println("Authorities for " + username + ": " + userDetails.getAuthorities());
            try {
                if (jwtUtil.validateToken(jwt, userDetails.getUsername()) && !blacklistService.isTokenBlacklisted(jwt)) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                    System.out.println("Authentication set for " + username);
                } else {
                    System.out.println("JWT validation failed or token blacklisted for " + username);
                }
            } catch (Exception e) {
                System.out.println("Validation error: " + e.getMessage());
            }
        } else if (username == null && authorizationHeader != null) {
            System.out.println("No username extracted from JWT");
        } else {
            System.out.println("No authentication set: username=" + username + ", existing auth=" + SecurityContextHolder.getContext().getAuthentication());
        }

        chain.doFilter(request, response);
    }
}