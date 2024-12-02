package com.compasspb.vitorsalb.pedido.domain.repository;

import com.compasspb.vitorsalb.pedido.domain.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

    Page<Order> findAllByEmail(Pageable pageable, String email);
}
