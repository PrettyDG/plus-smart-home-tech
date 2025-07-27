package ru.yandex.practicum.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.dto.shoppingCart.ShoppingCartDto;
import ru.yandex.practicum.dto.warehouse.AddProductToWarehouseRequest;
import ru.yandex.practicum.dto.warehouse.AddressDto;
import ru.yandex.practicum.dto.warehouse.BookedProductsDto;
import ru.yandex.practicum.dto.warehouse.NewWarehouseProduct;
import ru.yandex.practicum.service.WarehouseService;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/warehouse")
public class WarehouseController {
    private final WarehouseService warehouseService;

    @PutMapping
    public void addNewProductInWarehouse(@RequestBody NewWarehouseProduct newWarehouseProduct) {
        warehouseService.addNewProductInWarehouse(newWarehouseProduct);
    }

    @PostMapping("/check")
    public BookedProductsDto checkAlreadyBookedProductsInWarehouse(ShoppingCartDto shoppingCartDto) {
        return warehouseService.checkAlreadyBookedProductsInWarehouse(shoppingCartDto);
    }

    @PostMapping("/add")
    public void addProductToWarehouse(@RequestBody AddProductToWarehouseRequest request) {
        warehouseService.addProductToWarehouse(request);
    }

    @GetMapping("/address")
    public AddressDto getAdressWarehouse() {
        return warehouseService.getAdressWarehouse();
    }
}
