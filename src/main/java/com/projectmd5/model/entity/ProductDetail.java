package com.projectmd5.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@NoArgsConstructor
@Data
@Table(name = "product_detail")
public class ProductDetail {
   @Id
   @Column(length = 100)
   private String sku;

   @ManyToOne
   @JsonIgnore
   @ToString.Exclude
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
