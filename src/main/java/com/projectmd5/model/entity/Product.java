package com.projectmd5.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "product", uniqueConstraints = {
      @UniqueConstraint(columnNames = "productName"),
})
public class Product {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long productId;

   @Column(length = 100)
   private String productName;

   @Column(length = 100)
   private String sku;

   @Column(columnDefinition = "text")
   private String description;

   @ManyToOne
   @JoinColumn(name = "categoryId")
   private Category category;


   private BigDecimal unitPrice;
   private int quantity;
   private String imagePath;

   @Temporal(TemporalType.DATE)
   private Date createdAt = new Date();
   @Temporal(TemporalType.DATE)
   private Date updatedAt;

   @ManyToOne
   @JoinColumn(name = "wishListId")
   private WishList wishLists;
}
