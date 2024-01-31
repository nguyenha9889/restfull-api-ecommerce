package com.projectmd5.service;

import com.projectmd5.model.dto.order.*;
import com.projectmd5.model.entity.OrderDetail;
import com.projectmd5.model.entity.Orders;
import com.projectmd5.model.entity.User;

import java.util.List;

public interface IOrderService extends IGenericService<Orders, Long>{
   Orders createOrder(User user, OrderDetailRequest orderDetailRequest);
   OrderResponse findByUserAndOrderId(User user, Long id);
   void checkOut(User user, Long orderId, OrderRequest orderRequest);
   List<OrderDetailResponse> mapToOrderDetailListResponse(List<OrderDetail> orderDetails);
   OrderResponse mapToOrderResponse(Orders orders);
   OrderResponse updateOrderStatus(Long orderId, String status);
   OrderResponse cancelOrderWaiting(User user, Long orderId);
   OrderPageResponse getOrderPageByUser(User user, int pageNo, int pageSize, String sortBy, String sortDir);
   OrderPageResponse getOrderPageByUserAndStatus(User user, String status, int pageNo, int pageSize, String sortBy, String sortDir);
   OrderPageResponse getOrderPage(int pageNo, int pageSize, String sortBy, String sortDir);
   OrderPageResponse getOrderPageByStatus(String status, int pageNo, int pageSize, String sortBy, String sortDir);
}
