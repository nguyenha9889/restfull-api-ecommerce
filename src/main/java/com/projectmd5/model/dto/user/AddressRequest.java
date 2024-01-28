package com.projectmd5.model.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.projectmd5.constants.MessageConstant.*;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class AddressRequest {
   private Long addressId;

   @NotBlank(message = RECEIVED_NAME_NOT_BLANK)
   private String receiveName;

   @Pattern(regexp = "^(84|0[35789])+([0-9]{8})$", message = PHONE_INVALID)
   private String phone;

   @NotBlank(message = ADDRESS_NOT_BLANK)
   private String fullAddress;

   private boolean defaultAddress;
}
