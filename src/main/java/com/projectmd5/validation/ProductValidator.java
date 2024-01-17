package com.projectmd5.validation;

import com.projectmd5.model.dto.product.ProductRequest;
import com.projectmd5.service.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

import static com.projectmd5.constants.MessageConstant.*;

@Component
@RequiredArgsConstructor
public class ProductValidator implements Validator {
   private final IProductService productService;
   @Override
   public boolean supports(Class<?> clazz) {
      return ProductRequest.class.equals(clazz);
   }

   @Override
   public void validate(Object target, Errors errors) {
      ProductRequest request = (ProductRequest) target;

      ValidationUtils.rejectIfEmptyOrWhitespace(errors, "productName", FIELD_NOT_BLANK);
      ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description", FIELD_NOT_BLANK);

      if (!errors.hasFieldErrors()){
         if (productService.existProductName(request.getProductId(), request.getProductName())){
            errors.rejectValue("productName", PRODUCT_EXISTED);
         }
      }

      if (request.getImage() != null && request.getImage().getSize() > 0 && !isSupportedImageType(request.getImage())) {
         errors.rejectValue("image", FILE_UPLOAD_RULE);
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
