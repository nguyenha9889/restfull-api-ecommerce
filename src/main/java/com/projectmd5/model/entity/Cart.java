package com.projectmd5.model.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Cart {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long cartId;

   @ManyToOne
   @JoinColumn(name = "userId")
   private User user;

   @ManyToOne
   @JoinColumn(name = "productId")
   private Product product;

   @OneToOne
   @JoinColumn(name = "sku")
   private ProductDetail productDetail;

   private int quantity;
}
