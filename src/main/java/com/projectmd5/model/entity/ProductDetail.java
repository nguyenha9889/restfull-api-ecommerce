package com.projectmd5.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ProductDetail {
   @Id
   @Column(length = 100)
   @GeneratedValue(strategy = GenerationType.UUID)
   private String sku;

   @ManyToOne
   @JoinColumn(name = "productId")
   private Product product;

   @Column(length = 100)
   private EProductSize size;

   @Column(length = 100)
   private String dough;

   @Column(precision=10, scale=2)
   private BigDecimal unitPrice;
}
