package com.projectmd5.model.dto.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.projectmd5.model.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseUserResponse {
   @JsonIgnore
   private Long id;
   private String fullName;
   private String username;
   private String email;
   private String phone;
   private boolean status;
   private Set<Role> roles;
}
