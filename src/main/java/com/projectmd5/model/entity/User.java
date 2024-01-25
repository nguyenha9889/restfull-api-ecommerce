package com.projectmd5.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "user", uniqueConstraints = {
      @UniqueConstraint(columnNames = "username"),
      @UniqueConstraint(columnNames = "email"),
      @UniqueConstraint(columnNames = "phone")
})
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class User {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long userId;

   @Column(length = 100)
   private String fullName;

   @Column(length = 100)
   private String username;

   @Column(length = 100)
   private String email;

   @Column(length = 15)
   private String phone;

   @Column(length = 100)
   private String password;

   private boolean status;
   private String avatar;

   @Temporal(TemporalType.DATE)
   private Date createdAt = new Date();
   @Temporal(TemporalType.DATE)
   private Date updatedAt;

   @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
   @JoinTable(
         name = "user_role",
         joinColumns = @JoinColumn(name = "userId"),
         inverseJoinColumns = @JoinColumn(name = "roleId"))
   private Set<Role> roles;

   @OneToMany(mappedBy = "user")
   private List<Address> addresses;

   @OneToMany(mappedBy = "user")
   private List<WishList> wishLists;

   @JsonIgnore
   @ToString.Exclude
   @OneToMany(mappedBy = "user")
   private List<Cart> carts;

   @OneToMany(mappedBy = "user")
   private List<Orders> orders;
}
