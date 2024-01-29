package com.projectmd5.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Address {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long addressId;

   @JsonIgnore
   @ToString.Exclude
   @ManyToOne
   @JoinColumn(name = "userId")
   private User user;

   private String fullAddress;

   @Column(length = 15)
   private String phone;

   @Column(length = 50)
   private String receiveName;

   private boolean defaultAddress = false;
}
