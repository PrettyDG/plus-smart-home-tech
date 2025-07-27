package ru.yandex.practicum.service;

import ru.yandex.practicum.dto.shoppingCart.ShoppingCartDto;
import ru.yandex.practicum.dto.warehouse.AddProductToWarehouseRequest;
import ru.yandex.practicum.dto.warehouse.AddressDto;
import ru.yandex.practicum.dto.warehouse.BookedProductsDto;
import ru.yandex.practicum.dto.warehouse.NewWarehouseProduct;

public interface WarehouseService {
    void addNewProductInWarehouse(NewWarehouseProduct newWarehouseProduct);

    BookedProductsDto checkAlreadyBookedProductsInWarehouse(ShoppingCartDto shoppingCartDto);

    void addProductToWarehouse(AddProductToWarehouseRequest request);

    AddressDto getAdressWarehouse();
}
