package com.compasspb.vitorsalb.pedido.domain.repository;

import com.compasspb.vitorsalb.pedido.domain.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
