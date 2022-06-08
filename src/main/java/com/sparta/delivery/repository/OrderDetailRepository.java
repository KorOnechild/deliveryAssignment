package com.sparta.delivery.repository;

import com.sparta.delivery.model.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
}
