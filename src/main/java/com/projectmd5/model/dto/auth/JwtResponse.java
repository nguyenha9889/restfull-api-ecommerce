package com.projectmd5.model.dto.auth;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JwtResponse {
   @JsonIgnore
   private String username;
   private String accessToken;
   private String refreshToken;
   private final String type = "Bearer Token";
   private List<String> roles;
}