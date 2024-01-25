package com.projectmd5.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name="cart_item")
public class CartItem {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long cartItemId;

   @ManyToOne
   @JoinColumn(name = "cartId")
   @ToString.Exclude
   @JsonIgnore
   private Cart cart;

   @OneToOne
   @JoinColumn(name = "sku")
   private ProductDetail productDetail;
   private int quantity;
}
