package com.projectmd5.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Category {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long categoryId;

   @Column(length = 100)
   private String categoryName;

   @Column(columnDefinition = "text")
   private String description;

   private boolean status;

   @JsonIgnore
   @OneToMany(mappedBy = "category")
   private List<Product> products;
}
