package com.projectmd5.model.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cart {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long cartId;
   @OneToOne
   @JoinColumn(name = "userId")
   private User user;

   @OneToMany
   @JoinColumn(name = "productId")
   private List<Product> product;
   private int orderQuantity;
}
