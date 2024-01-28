package com.projectmd5.model.dto.order;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {
   private Long orderId;
   private String receiveName;
   private String receivePhone;
   private String receiveAddress;
   private Long totalPrice;
   private String status;
   private List<OrderDetailResponse> orderDetails;
   @JsonFormat(pattern = "MM-dd-yyyy")
   private Date createdAt;
   @JsonFormat(pattern = "MM-dd-yyyy")
   private Date updatedAt;
}
