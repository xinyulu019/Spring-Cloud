package com.xinyulu.inventoryservice.repository;

import com.xinyulu.inventoryservice.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    //@Query(value = "select * from T_Inventory where skuCode in (?1)", nativeQuery = true)
    List<Inventory> findBySkuCodeIn(List<String> skuCode);
}
