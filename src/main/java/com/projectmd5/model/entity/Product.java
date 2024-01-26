package com.projectmd5.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

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

   @Column(columnDefinition = "text")
   private String description;

   @OneToMany(mappedBy = "product")
   private List<ProductDetail> productDetails;

   @ManyToOne
   @JoinColumn(name = "categoryId")
   private Category category;

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
   private List<OrderDetail> orderDetails;
}
