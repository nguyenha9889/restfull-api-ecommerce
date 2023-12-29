package com.projectmd5.model.dto.user;

import com.projectmd5.validation.PhoneUnique;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class AddressRequest {
   private Long addressId;
   @NotNull
   private Long userId;
   @NotBlank(message = "Địa chỉ không được để trống")
   @Length(min = 20, message = "Địa chỉ yêu cầu tối thiểu 20 ký tự")
   private String fullAddress;
   @Pattern(regexp = "^(84|0[3|5|7|8|9])+([0-9]{8})$", message = "Số điện thoại không hợp lệ")
   @PhoneUnique
   private String phone;
   private String receiveName;
}
