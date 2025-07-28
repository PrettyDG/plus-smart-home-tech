package ru.yandex.practicum.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.dto.shoppingCart.ChangeProductQuantityRequest;
import ru.yandex.practicum.dto.shoppingCart.ShoppingCartDto;
import ru.yandex.practicum.service.ShoppingCartService;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/shopping-cart")
public class ShoppingCartController {
    private final ShoppingCartService shoppingCartService;

    @GetMapping
    public ShoppingCartDto getShoppingCartByUsername(@RequestParam String username) {
        return shoppingCartService.getShoppingCartByUsername(username);
    }

    @PutMapping
    public ShoppingCartDto addProductsToCart(@RequestParam String username,
                                             @RequestBody Map<UUID, Long> items) {
        return shoppingCartService.addProductsToCart(username, items);
    }

    @DeleteMapping
    public void deactivateShoppingCart(@RequestParam String username) {
        shoppingCartService.deactivateShoppingCart(username);
    }

    @PostMapping("/remove")
    public ShoppingCartDto removeProductsFromCart(@RequestParam String username,
                                                  @RequestBody List<UUID> productIds) {
        return shoppingCartService.removeProductsFromCart(username, productIds);
    }

    @PostMapping("/change-quantity")
    public ShoppingCartDto changeQuantity(@RequestParam String username,
                                          @RequestBody ChangeProductQuantityRequest quantityRequest) {
        return shoppingCartService.changeQuantity(username, quantityRequest);
    }
}
