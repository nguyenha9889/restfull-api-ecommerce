package com.projectmd5.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "order_detail")
public class OrderDetail {
   @EmbeddedId
   private OrderDetailId orderDetailId;

   @JsonIgnore
   @ToString.Exclude
   @ManyToOne
   @MapsId("orderId")
   @JoinColumn(name = "orderId")
   private Orders order;

   @ManyToOne
   @MapsId("productId")
   @JoinColumn(name = "productId")
   private Product product;

   @Column(length = 100)
   private String productName;

   @OneToOne
   @JoinColumn(name = "productDetailId")
   private ProductDetail productDetail;

   private int quantity;
}
