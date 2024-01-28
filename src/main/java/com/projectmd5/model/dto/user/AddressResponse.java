package com.projectmd5.model.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressResponse {
   private Long addressId;
   private String receiveName;
   private String phone;
   private String fullAddress;
   private boolean defaultAddress;
}
