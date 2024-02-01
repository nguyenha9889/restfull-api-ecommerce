package com.projectmd5.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

@Entity
@NoArgsConstructor
@Data
@Table(name = "product_detail")
public class ProductDetail {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long productDetailId;

   @ToString.Exclude
   @JsonIgnore
   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "productId")
   private Product product;

   @Enumerated(EnumType.STRING)
   @Column(length = 100)
   private EProductSize size;

   @Column(length = 100)
   private String dough;

   @Column(precision=10, scale=2)
   private BigDecimal unitPrice;
}
