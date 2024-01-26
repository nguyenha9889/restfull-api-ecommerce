package com.projectmd5.model.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
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

   @OneToMany(mappedBy = "cart")
   private List<CartItem> cartItems;

   @Column(precision=10, scale=2)
   private BigDecimal totalPrice;
}
