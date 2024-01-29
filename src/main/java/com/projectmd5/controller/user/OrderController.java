package com.projectmd5.controller.user;

import com.projectmd5.model.dto.MessageResponse;
import com.projectmd5.model.dto.order.OrderDetailRequest;
import com.projectmd5.model.dto.order.OrderPageResponse;
import com.projectmd5.model.dto.order.OrderRequest;
import com.projectmd5.model.dto.order.OrderResponse;
import com.projectmd5.model.entity.Orders;
import com.projectmd5.model.entity.User;
import com.projectmd5.security.principal.UserDetailCustom;
import com.projectmd5.service.impl.OrderService;
import com.projectmd5.service.impl.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import static com.projectmd5.constants.PathConstant.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(API_V1_USER)
public class OrderController {
   private final OrderService orderService;
   private final UserService userService;

   @GetMapping(ORDERS)
   public ResponseEntity<?> getAllByUser(
         @RequestParam(defaultValue = "0") int pageNo,
         @RequestParam(defaultValue = "10") int pageSize,
         @RequestParam(defaultValue = "updatedAt") String sortBy,
         @RequestParam(defaultValue = "desc") String sortDir) {
      UserDetailCustom userDetail = (UserDetailCustom) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
      User user = userService.findById(userDetail.getId());
      OrderPageResponse response = orderService.getOrderPageByUser(user, pageNo, pageSize, sortBy, sortDir);
      return ResponseEntity.ok(response);
   }

   @GetMapping(ORDERS_STATUS)
   public ResponseEntity<?> getAllByUserAndStatus(
         @PathVariable String status,
         @RequestParam(defaultValue = "0") int pageNo,
         @RequestParam(defaultValue = "10") int pageSize,
         @RequestParam(defaultValue = "updatedAt") String sortBy,
         @RequestParam(defaultValue = "desc") String sortDir) {
      UserDetailCustom userDetail = (UserDetailCustom) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
      User user = userService.findById(userDetail.getId());
      OrderPageResponse response = orderService.getOrderPageByUserAndStatus(user, status, pageNo, pageSize, sortBy, sortDir);
      return ResponseEntity.ok(response);
   }

   @PutMapping(ORDERS_ID_CANCEL)
   public ResponseEntity<?> cancelOrder(@PathVariable Long orderId) {
      OrderResponse response = orderService.cancelOrderWaiting(orderId);
      return ResponseEntity.ok(response);
   }

   @PostMapping(ORDERS)
   public ResponseEntity<?> addOrder(@Valid @RequestBody OrderDetailRequest request) {
      UserDetailCustom userDetail = (UserDetailCustom) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
      User user = userService.findById(userDetail.getId());
      Orders orders = orderService.createOrder(user, request);
      OrderResponse response = orderService.mapToOrderResponse(orders);
      return new ResponseEntity<>(response, HttpStatus.CREATED);
   }

   @PutMapping(ORDERS_ID)
   public ResponseEntity<?> checkOut(@PathVariable Long orderId, @RequestBody OrderRequest request) {
      orderService.checkOut(orderId, request);
      MessageResponse response = new MessageResponse("Order successfully");
      return ResponseEntity.ok(response);
   }
}
