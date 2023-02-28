package com.xinyulu.orderserver.repository;

import com.xinyulu.orderserver.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository <Order, Long> {

}
