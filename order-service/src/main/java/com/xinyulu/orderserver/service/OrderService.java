package com.xinyulu.orderserver.service;

import com.xinyulu.orderserver.dto.InventoryResponse;
import com.xinyulu.orderserver.dto.OrderLineItemsDto;
import com.xinyulu.orderserver.dto.OrderRequest;
import com.xinyulu.orderserver.model.Order;
import com.xinyulu.orderserver.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {
    private final OrderRepository orderRepository;
    private final WebClient.Builder webClientBuilder;

    public String placeOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        List<String> skuCodes = orderRequest.getOrderLineItemsDtoList()
                .stream()
                .map(OrderLineItemsDto::getSkuCode)
                .toList();

        InventoryResponse[] inventoryResponsArray = webClientBuilder.build().get()
                .uri("http://inventory-service/api/inventory",
                        uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes).build())
                .retrieve()
                .bodyToMono(InventoryResponse[].class)
                .block();

        boolean allProductsInStock = Arrays.stream(inventoryResponsArray)
                .allMatch(InventoryResponse::isInStock);
        if (allProductsInStock) {
            orderRepository.save(order);
            return "Order Placed Successfully";
        } else {
            return "Product is not in stock, please try again later";
        }

    }

    private OrderLineItemsDto mapToDto(OrderLineItemsDto orderLineItemsDto) {
        OrderLineItemsDto orderLineItems = new OrderLineItemsDto();
        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
        return orderLineItems;
    }
}
