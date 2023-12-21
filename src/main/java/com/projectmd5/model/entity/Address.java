package com.projectmd5.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Address {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long addressId;

   @ManyToOne
   @JoinColumn(name = "userId")
   private User user;

   private String fullAddress;

   @Column(length = 15)
   private String phone;

   @Column(length = 50)
   private String receiveName;

}
