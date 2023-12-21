package com.projectmd5.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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

   @OneToMany(mappedBy = "productId")
   private List<Product> product;
}
