package com.xinyulu.inventoryservice.model;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "TT_Inventory")
@NoArgsConstructor
@AllArgsConstructor
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IID", unique = true, nullable = false)
    private Long id;
    @Column(name = "CName", nullable = false)
    private String skuCode;
    @Column(name = "Quantity", nullable = false)
    private Integer quantity;
}
