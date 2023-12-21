package com.projectmd5.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Orders {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long orderId;
   @GeneratedValue(strategy = GenerationType.UUID)
   @Size(max = 100)
   private String serialNumber;

   @ManyToOne
   @JoinColumn(name = "userId")
   private User user;

   @OneToMany(mappedBy = "order")
   private List<OrderDetail> orderDetails;

   private BigDecimal totalPrice;

   @Enumerated(EnumType.STRING)
   private EOrderStatus status;

   @Column(length = 100)
   private String note;

   @Column(length = 100)
   private String receiveName;

   private String receiveAddress;

   @Column(length = 15)
   private String receivePhone;

   @Temporal(TemporalType.DATE)
   private Date createdAt = new Date();

   @Temporal(TemporalType.DATE)
   private Date receivedAt;
}
