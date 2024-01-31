package com.projectmd5.repository;

import com.projectmd5.model.entity.EOrderStatus;
import com.projectmd5.model.entity.Orders;
import com.projectmd5.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IOrderRepository extends JpaRepository<Orders, Long>{
   Page<Orders> findAllByStatus(EOrderStatus status, Pageable pageable);
   Page<Orders> findAllByUser(User user, Pageable pageable);
   Page<Orders> findAllByUserAndStatus(User user, EOrderStatus status, Pageable pageable);
   Optional<Orders> findByUserAndOrderId(User user, Long id);
}
