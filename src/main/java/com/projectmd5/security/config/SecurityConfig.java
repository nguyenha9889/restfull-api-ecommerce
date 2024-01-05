package com.projectmd5.security.config;

import com.projectmd5.security.jwt.JwtAuthTokenFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity(jsr250Enabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

   private final UserDetailsService userDetailsService;
   private final AuthEntryPointJwt authEntryPointJwt; // xử lý exception
   private final JwtAuthTokenFilter jwtAuthTokenFilter;
   private final PasswordEncoder passwordEncoder;
   private final AccessDeniedHandler accessDeniedHandler;
   @Bean
   public AuthenticationManager authManager(AuthenticationConfiguration config) throws Exception {
      return config.getAuthenticationManager();
   }
   @Bean
   public AuthenticationProvider authProvider(){
      DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
      authProvider.setUserDetailsService(userDetailsService);
      authProvider.setPasswordEncoder(passwordEncoder);
      return authProvider;
   }

   @Bean
   public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
      http.cors(Customizer.withDefaults())
            .csrf(AbstractHttpConfigurer::disable)
            .exceptionHandling(ex -> ex.authenticationEntryPoint(authEntryPointJwt))
            .exceptionHandling(ex -> ex.accessDeniedHandler(accessDeniedHandler))
            .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth ->
                  auth.requestMatchers("/api.myservice.com/v1/auth/**").permitAll()
                        .requestMatchers("/api.myservice.com/v1/categories").permitAll()
                        .requestMatchers("/api.myservice.com/v1/products/**").permitAll()
                        .requestMatchers("/api.myservice.com/v1/admin/**").hasRole("ADMIN")
                        .requestMatchers("/api.myservice.com/v1/pm/**").hasAnyRole("ADMIN","PM")
                        .requestMatchers("/api.myservice.com/v1/user/**").hasAnyRole("ADMIN","PM", "USER")
                        .anyRequest().authenticated());

      http.authenticationProvider(authProvider());
      http.addFilterBefore(jwtAuthTokenFilter, UsernamePasswordAuthenticationFilter.class);

      return http.build();
   }
}
