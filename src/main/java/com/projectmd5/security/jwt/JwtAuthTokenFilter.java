package com.projectmd5.security.jwt;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthTokenFilter extends OncePerRequestFilter {

   private final UserDetailsService userDetailsService;
   private final JwtTokenBuilder jwtTokenBuilder;

   @Override
   protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
      try {
         String token = getTokenFromRequest(request);
         if (token != null && jwtTokenBuilder.validateToken(token)) {
            String username = jwtTokenBuilder.getUserNameFromToken(token);
            UserDetails principal = userDetailsService.loadUserByUsername(username);

            UsernamePasswordAuthenticationToken authentication =
                  new UsernamePasswordAuthenticationToken(
                        principal, null, principal.getAuthorities());

            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
         }
      } catch (Exception e) {
         log.error("Cannot set user authentication: {}", e.getMessage());
      }
      filterChain.doFilter(request, response);
   }

   private String getTokenFromRequest(HttpServletRequest request){
      String authorization = request.getHeader("Authorization");
      if (authorization!=null&&authorization.startsWith("Bearer ")){
         return authorization.substring("Bearer ".length());
      }
      return null;
   }
}
