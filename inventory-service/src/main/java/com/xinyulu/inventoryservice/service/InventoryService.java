package com.xinyulu.inventoryservice.service;

import com.xinyulu.inventoryservice.dto.InventoryResponse;
import com.xinyulu.inventoryservice.model.Inventory;
import com.xinyulu.inventoryservice.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryService {
    private final InventoryRepository inventoryRepository;

    @SneakyThrows //測試延遲
    public List<InventoryResponse> isInStock(List<String> skuCode) {
        //測試延遲
//        log.info("Wait Started");
//        Thread.sleep(10000);
//        log.info("Wait Ended");
        log.info("Checking Inventory");
        return inventoryRepository.findBySkuCodeIn(skuCode).stream()
                .map(inventory ->
                        InventoryResponse.builder()
                                .skuCode(inventory.getSkuCode())
                                .isInStock(inventory.getQuantity() > 0)
                                .build()
                ).toList();
    }

    public String placeInventory() {
        Inventory inventory = new Inventory();
        inventory.setSkuCode("iphone13-red");
        inventory.setQuantity(10);

        inventoryRepository.save(inventory);
        return "inventory Placed Successfully";
    }
}
