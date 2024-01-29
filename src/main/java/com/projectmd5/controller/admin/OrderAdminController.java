package com.projectmd5.controller.admin;

import com.projectmd5.model.dto.order.OrderPageResponse;
import com.projectmd5.model.dto.order.OrderResponse;
import com.projectmd5.model.entity.Orders;
import com.projectmd5.service.IOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.projectmd5.constants.PathConstant.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(API_V1_ADMIN)
public class OrderAdminController {
   private final IOrderService orderService;
   @GetMapping(ORDERS)
   public ResponseEntity<?> getAll(
         @RequestParam(name = "pageNo", defaultValue = "0", required = false) int pageNo,
         @RequestParam(name = "pageSize", defaultValue = "10", required = false) int pageSize,
         @RequestParam(name = "sortBy", defaultValue = "updatedAt", required = false) String sortBy,
         @RequestParam(name = "sortDir", defaultValue = "asc", required = false) String sortDir,
         @RequestParam(name = "status", defaultValue = "", required = false) String status
   ) {
      OrderPageResponse response;
      if (status.isEmpty()) {
         response = orderService.getOrderPage(pageNo, pageSize, sortBy, sortDir);
      } else {
         response = orderService.getOrderPageByStatus(status, pageNo, pageSize, sortBy, sortDir);
      }
      return ResponseEntity.ok(response);
   }

   @GetMapping(ORDERS_ID)
   public ResponseEntity<?> getOrderById(@PathVariable Long orderId) {
      Orders orders = orderService.findById(orderId);
      OrderResponse response = orderService.mapToOrderResponse(orders);
      return ResponseEntity.ok(response);
   }

   @PutMapping(ORDERS_ID)
   public ResponseEntity<?> updateOrderStatus(@PathVariable Long orderId, @RequestBody String status) {
      OrderResponse response = orderService.updateOrderStatus(orderId, status);
      return ResponseEntity.ok(response);
   }
}
