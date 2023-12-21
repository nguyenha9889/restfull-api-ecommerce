package com.projectmd5.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "user", uniqueConstraints = {
      @UniqueConstraint(columnNames = {"username", "email", "phone"}),
})
@AllArgsConstructor
@NoArgsConstructor
@Data
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
   private String address;

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
}
