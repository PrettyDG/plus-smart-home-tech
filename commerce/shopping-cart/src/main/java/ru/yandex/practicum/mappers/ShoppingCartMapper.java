package ru.yandex.practicum.mappers;

import ru.yandex.practicum.dto.shoppingCart.ShoppingCartDto;
import ru.yandex.practicum.models.ShoppingCart;

public class ShoppingCartMapper {
    public static ShoppingCartDto toDto(ShoppingCart shoppingCart) {
        return ShoppingCartDto.builder()
                .cartId(shoppingCart.getCartId())
                .products(shoppingCart.getProducts())
                .build();
    }

    public static ShoppingCart toEntity(ShoppingCartDto shoppingCartDto) {
        return ShoppingCart.builder()
                .cartId(shoppingCartDto.getCartId())
                .products(shoppingCartDto.getProducts())
                .build();
    }
}
