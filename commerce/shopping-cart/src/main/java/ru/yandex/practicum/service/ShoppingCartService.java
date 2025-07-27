package ru.yandex.practicum.service;

import ru.yandex.practicum.dto.shoppingCart.ChangeProductQuantityRequest;
import ru.yandex.practicum.dto.shoppingCart.ShoppingCartDto;
import ru.yandex.practicum.models.ShoppingCart;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface ShoppingCartService {

    ShoppingCart getCart(String username);

    ShoppingCartDto getShoppingCartByUsername(String username);

    ShoppingCartDto addProductsToCart(String username, Map<UUID, Long> items);

    void deactivateShoppingCart(String username);

    ShoppingCartDto removeProductsFromCart(String username, List<UUID> productIds);

    ShoppingCartDto changeQuantity(String username, ChangeProductQuantityRequest quantityRequest);

}
