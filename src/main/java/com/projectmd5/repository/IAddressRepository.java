package com.projectmd5.repository;

import com.projectmd5.model.entity.Address;
import com.projectmd5.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IAddressRepository extends JpaRepository<Address, Long> {
   List<Address> findAllByUser(User user);
}
