package com.projectmd5.service.impl;

import com.projectmd5.exception.BadRequestException;
import com.projectmd5.exception.ResourceNotFoundException;
import com.projectmd5.model.dto.order.*;
import com.projectmd5.model.dto.product.ProductDetailResponse;
import com.projectmd5.model.entity.*;
import com.projectmd5.repository.IOrderDetailRepository;
import com.projectmd5.repository.IOrderRepository;
import com.projectmd5.service.IOrderService;
import com.projectmd5.service.IProductDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.projectmd5.constants.MessageConstant.ADDRESS_DEFAULT_NOT_FOUND;
import static com.projectmd5.constants.MessageConstant.ORDER_NOT_FOUND;

@RequiredArgsConstructor
@Service
public class OrderService implements IOrderService {
   private final IOrderRepository orderRepository;
   private final IOrderDetailRepository orderDetailRepo;
   private final CartService cartService;
   private final IProductDetailService productDetailService;
   @Override
   public List<Orders> findAll() {
      return orderRepository.findAll();
   }

   @Override
   public Orders findById(Long id) {
      return orderRepository.findById(id).orElseThrow(
            () -> new ResourceNotFoundException(ORDER_NOT_FOUND)
      );
   }

   @Override
   public OrderResponse findByUserAndOrderId(User user, Long id) {
      Orders orders = orderRepository.findByUserAndOrderId(user, id).orElseThrow(
            () -> new ResourceNotFoundException(ORDER_NOT_FOUND)
      );
      return mapToOrderResponse(orders);
   }

   @Override
   public Orders createOrder(User user, OrderDetailRequest orderDetailRequest) {
      Address address = user.getAddresses().stream().filter(Address::isDefaultAddress).findFirst().orElseThrow(
            () -> new ResourceNotFoundException(ADDRESS_DEFAULT_NOT_FOUND)
      );
      Orders orders = orderRepository.save(Orders.builder()
            .user(user)
            .receiveName(address.getReceiveName())
            .receivePhone(address.getPhone())
            .receiveAddress(address.getFullAddress())
            .status(EOrderStatus.WAITING)
            .createdAt(new Date())
            .updatedAt(new Date())
            .build());

      List<OrderDetail> orderDetails = createOrderDetails(orders, orderDetailRequest);
      orders.setOrderDetails(orderDetails);

      BigDecimal totalPrice = orderDetails.stream()
            .map(orderDetail -> {
               if (orderDetail.getProductDetail().getUnitPrice() == null) {
                  return BigDecimal.ZERO;
               } else {
                  return orderDetail.getProductDetail().getUnitPrice().multiply(BigDecimal.valueOf(orderDetail.getQuantity()));
               }
            })
            .reduce(BigDecimal.ZERO, BigDecimal::add);
      orders.setTotalPrice(totalPrice);
      return orderRepository.save(orders);
   }

   private List<OrderDetail> createOrderDetails(Orders order, OrderDetailRequest orderDetailRequest) {
      List<CartCheckOut> cartIds = orderDetailRequest.getCartIds();
      List<OrderDetail> orderDetails = new ArrayList<>();
      cartIds.forEach(cartId -> {
         Cart cart = cartService.findByUserAndCartId(order.getUser(), cartId.getCartId());
         OrderDetailId orderDetailId = new OrderDetailId(order.getOrderId(), cart.getProduct().getProductId());
         OrderDetail orderDetail = orderDetailRepo.save(OrderDetail.builder()
               .orderDetailId(orderDetailId)
               .order(order)
               .product(cart.getProduct())
               .productName(cart.getProduct().getProductName())
               .productDetail(cart.getProductDetail())
               .quantity(cart.getQuantity())
               .build());

         orderDetails.add(orderDetail);
         cartService.delete(cart);
      });
      return orderDetails;
   }

   @Override
   public List<OrderDetailResponse> mapToOrderDetailListResponse(List<OrderDetail> orderDetails) {
      List<OrderDetailResponse> orderDetailResponses = new ArrayList<>();
      orderDetails.forEach(orderDetail -> {
         OrderDetailResponse orderDetailResponse = new OrderDetailResponse();
         orderDetailResponse.setProductId(orderDetail.getProduct().getProductId());
         orderDetailResponse.setProductName(orderDetail.getProductName());
         orderDetailResponse.setImagePath(orderDetail.getProduct().getImagePath());

         ProductDetailResponse productDetail = productDetailService.mapperToDetailResponse(orderDetail.getProductDetail());
         orderDetailResponse.setProductDetail(productDetail);
         orderDetailResponse.setQuantity(orderDetail.getQuantity());
         orderDetailResponses.add(orderDetailResponse);
      });
      return orderDetailResponses;
   }

   @Override
   public OrderResponse mapToOrderResponse(Orders orders) {
      OrderResponse orderResponse = new OrderResponse();
      orderResponse.setOrderId(orders.getOrderId());
      orderResponse.setReceiveName(orders.getReceiveName());
      orderResponse.setReceivePhone(orders.getReceivePhone());
      orderResponse.setReceiveAddress(orders.getReceiveAddress());
      orderResponse.setTotalPrice(orders.getTotalPrice().longValue());
      orderResponse.setStatus(orders.getStatus().name());
      orderResponse.setOrderDetails(mapToOrderDetailListResponse(orders.getOrderDetails()));
      orderResponse.setCreatedAt(orders.getCreatedAt());
      orderResponse.setUpdatedAt(orders.getUpdatedAt());
      return orderResponse;
   }

   @Override
   public void checkOut(User user, Long orderId, OrderRequest orderRequest) {
      Orders orders = orderRepository.findByUserAndOrderId(user, orderId).orElseThrow(
            () -> new ResourceNotFoundException(ORDER_NOT_FOUND)
      );
      orders.setNote(orderRequest.getNote());
      orders.setStatus(EOrderStatus.CONFIRM);
      orderRepository.save(orders);
   }

   // get all orders of user
   @Override
   public OrderPageResponse getOrderPageByUser(User user, int pageNo, int pageSize, String sortBy, String sortDir){
      Pageable pageable = createPageable(pageNo, pageSize, sortBy, sortDir);
      Page<Orders> pages = orderRepository.findAllByUser(user, pageable);
      List<Orders> ordersList = pages.getContent();
      List<OrderResponse> data = ordersList.stream()
            .map(this::mapToOrderResponse)
            .toList();
      return OrderPageResponse.builder()
            .data(data)
            .pageNo(pageable.getPageNumber())
            .pageSize(pageable.getPageSize())
            .totalElements(pages.getTotalElements())
            .totalPages(pages.getTotalPages())
            .last(pages.isLast())
            .build();
   }

   // get all orders of user and status
   @Override
   public OrderPageResponse getOrderPageByUserAndStatus(User user, String status, int pageNo, int pageSize, String sortBy, String sortDir){
      Pageable pageable = createPageable(pageNo, pageSize, sortBy, sortDir);
      Page<Orders> pages = orderRepository.findAllByUserAndStatus(user, EOrderStatus.valueOf(status), pageable);
      List<Orders> ordersList = pages.getContent();
      List<OrderResponse> data = ordersList.stream()
            .map(this::mapToOrderResponse)
            .toList();
      return OrderPageResponse.builder()
            .data(data)
            .pageNo(pageable.getPageNumber())
            .pageSize(pageable.getPageSize())
            .totalElements(pages.getTotalElements())
            .totalPages(pages.getTotalPages())
            .last(pages.isLast())
            .build();
   }

   @Override
   public OrderResponse cancelOrderWaiting(User user, Long orderId) {
      Orders orders = orderRepository.findByUserAndOrderId(user, orderId).orElseThrow(
            () -> new ResourceNotFoundException(ORDER_NOT_FOUND)
      );
      if (!orders.getStatus().equals(EOrderStatus.WAITING)) {
         throw new BadRequestException("Order is confirmed, can not cancel");
      }
      orders.setStatus(EOrderStatus.CANCEL);
      orders.setUpdatedAt(new Date());
      Orders updateOrder = orderRepository.save(orders);
      return mapToOrderResponse(updateOrder);
   }

   //=====================================ADMIN CONTROLLER==================================

   private Pageable createPageable(int pageNo, int pageSize, String sortBy, String sortDir){
      Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())
            ? Sort.by(sortBy).ascending()
            : Sort.by(sortBy).descending();
      return PageRequest.of(pageNo, pageSize, sort);
   }

   // get all orders for admin
   @Override
   public OrderPageResponse getOrderPage(int pageNo, int pageSize, String sortBy, String sortDir){
      Pageable pageable = createPageable(pageNo, pageSize, sortBy, sortDir);
      Page<Orders> pages = orderRepository.findAll(pageable);
      List<Orders> ordersList = pages.getContent();
      List<OrderResponse> data = ordersList.stream()
            .map(this::mapToOrderResponse)
            .toList();
      return OrderPageResponse.builder()
            .data(data)
            .pageNo(pageable.getPageNumber())
            .pageSize(pageable.getPageSize())
            .totalElements(pages.getTotalElements())
            .totalPages(pages.getTotalPages())
            .last(pages.isLast())
            .build();
   }

   // get all orders for admin by status
   @Override
   public OrderPageResponse getOrderPageByStatus(String status, int pageNo, int pageSize, String sortBy, String sortDir){
      Pageable pageable = createPageable(pageNo, pageSize, sortBy, sortDir);
      Page<Orders> pages = orderRepository.findAllByStatus(EOrderStatus.valueOf(status.trim().toUpperCase()), pageable);
      List<Orders> ordersList = pages.getContent();
      List<OrderResponse> data = ordersList.stream()
            .map(this::mapToOrderResponse)
            .toList();
      return OrderPageResponse.builder()
            .data(data)
            .pageNo(pageable.getPageNumber())
            .pageSize(pageable.getPageSize())
            .totalElements(pages.getTotalElements())
            .totalPages(pages.getTotalPages())
            .last(pages.isLast())
            .build();
   }

   // admin update status for order
   @Override
   public OrderResponse updateOrderStatus(Long orderId, String status) {
      Orders orders = findById(orderId);
      orders.setStatus(EOrderStatus.valueOf(status));
      orders.setUpdatedAt(new Date());
      Orders updateOrder = orderRepository.save(orders);
      return mapToOrderResponse(updateOrder);
   }
}
