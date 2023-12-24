package com.projectmd5.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class WishList {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long wishListId;

   @ManyToOne
   @JoinColumn(name = "userId")
   private User user;

   @ManyToOne
   @JoinColumn(name = "productId")
   private Product product;
}
