package com.projectmd5.validation;

import com.projectmd5.model.dto.user.AccountRequest;
import com.projectmd5.service.IAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

@Component
@RequiredArgsConstructor
public class AccountValidator implements Validator {
   private final IAccountService accountService;
   @Override
   public boolean supports(Class<?> clazz) {
      return AccountRequest.class.equals(clazz);
   }

   @Override
   public void validate(Object target, Errors errors) {
      AccountRequest request = (AccountRequest) target;

      ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "Họ tên không để trống");
      ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "Địa chỉ email không để trống");
      ValidationUtils.rejectIfEmptyOrWhitespace(errors, "phone", "Số điện thoại không để trống");

      if (!errors.hasFieldErrors()){
         if (!request.getEmail().matches("^[a-zA-Z]+[a-zA-Z0-9]*@{1}[a-zA-Z]+mail.com$")){
            errors.rejectValue("email", "Địa chỉ email không hợp lệ");
         }

         if (!request.getPhone().matches("^(84|0[3|5|7|8|9])+([0-9]{8})$")){
            errors.rejectValue("phone", "Số điện thoại không hợp lệ");
         }

         if (accountService.existsByEmail(request.getUserId(), request.getEmail())) {
            errors.rejectValue("email", "Địa chỉ email đã được đăng ký");
         }

         if (accountService.existsByPhone(request.getUserId(), request.getEmail())) {
            errors.rejectValue("phone", "Số điện thoại đã được đăng ký");
         }
      }

      if (request.getImage() != null && request.getImage().getSize() > 0 && !isSupportedImageType(request.getImage())) {
         errors.rejectValue("image", "Hỗ trợ định dạng ảnh png, jpg và jpeg");
      } else if (request.getImage() != null && request.getImage().getSize() > 1024*1024){
         errors.rejectValue("image", "Dung lượng ảnh thấp hơn 1MB");
      }
   }

   private boolean isSupportedImageType(MultipartFile image) {
      String imageType = StringUtils.getFilenameExtension(image.getOriginalFilename());
      assert imageType != null;
      return imageType.equals("png")
            || imageType.equals("jpg")
            || imageType.equals("jpeg");
   }
}
