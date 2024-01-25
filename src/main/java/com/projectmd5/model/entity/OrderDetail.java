package com.projectmd5.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "order_detail")
public class OrderDetail {
   @EmbeddedId
   private OrderDetailId orderDetailId;

   @ManyToOne
   @MapsId("orderId")
   @JoinColumn(name = "orderId")
   private Orders order;

   @ManyToOne
   @MapsId("productId")
   @JoinColumn(name = "productId")
   private Product product;

   @Column(length = 100)
   private String name;

   @Column(precision=10, scale=2)
   private BigDecimal unitPrice;

   private int quantity;
}
