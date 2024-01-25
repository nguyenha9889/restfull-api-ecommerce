package com.projectmd5.model.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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

   @OneToMany(mappedBy = "cart")
   private List<CartItem> cartItems;

   private int quantity;
}
