package com.projectmd5.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "category", uniqueConstraints = {
      @UniqueConstraint(columnNames = "categoryName"),
})
public class Category {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long categoryId;

   @Column(length = 100)
   private String categoryName;

   @Column(columnDefinition = "text")
   private String description;

   private boolean status;

   @OneToMany(mappedBy = "category")
   private List<Product> products;
}
