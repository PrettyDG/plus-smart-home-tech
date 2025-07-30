package ru.yandex.practicum.dto.warehouse;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.dto.delivery.ShippedForDelivery;
import ru.yandex.practicum.dto.order.AssemblyProductsByOrder;
import ru.yandex.practicum.dto.shoppingCart.ShoppingCartDto;

import java.util.Map;

@FeignClient(name = "warehouse", path = "/api/v1/warehouse")
public interface WarehouseClient {
    @PostMapping("/check")
    BookedProductsDto checkAvailableProducts(@RequestBody ShoppingCartDto shoppingCartDto);
    @PostMapping("/assembly")
    BookedProductsDto reserveProducts(@RequestBody AssemblyProductsByOrder request);

    @GetMapping("/address")
    BookedProductsDto getAddressWarehouse();

    @PostMapping("/shipped")
    void shippedToDelivery(@RequestBody ShippedForDelivery deliveryRequest);

    @PostMapping("/return")
    void returnProductsToWarehouse(@RequestBody Map<String, Long> products);
}