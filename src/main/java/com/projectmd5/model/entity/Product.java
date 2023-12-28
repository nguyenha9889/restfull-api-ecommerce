package com.projectmd5.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "product", uniqueConstraints = {
      @UniqueConstraint(columnNames = "productName"),
      @UniqueConstraint(columnNames = "sku"),
})
public class Product {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long productId;

   @Column(length = 100)
   private String productName;

   @Column(length = 100)
   @GeneratedValue(strategy = GenerationType.UUID)
   private String sku = UUID.randomUUID().toString();

   @Column(length = 100)
   private String size;

   @Column(length = 100)
   private String dough;

   @Column(columnDefinition = "text")
   private String description;

   @ManyToOne
   @JoinColumn(name = "categoryId")
   private Category category;

   @Column(precision=10, scale=2)
   private BigDecimal unitPrice;

   private String imagePath;

   @Temporal(TemporalType.DATE)
   private Date createdAt;
   @Temporal(TemporalType.DATE)
   private Date updatedAt;

   @JsonIgnore
   @OneToMany(mappedBy = "product")
   private List<WishList> wishLists;
   @JsonIgnore
   @OneToMany(mappedBy = "product")
   private List<Cart> carts;
   @JsonIgnore
   @OneToMany(mappedBy = "product")
   private List<OrderDetail> orderDetails;
}
