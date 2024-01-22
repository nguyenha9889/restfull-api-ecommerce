package com.projectmd5.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
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

   @JsonIgnore
   @ToString.Exclude
   @OneToMany(mappedBy = "category")
   private List<Product> products;
}
