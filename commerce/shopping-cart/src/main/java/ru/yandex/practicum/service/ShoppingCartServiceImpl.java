package ru.yandex.practicum.service;

import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.dto.shoppingCart.ChangeProductQuantityRequest;
import ru.yandex.practicum.dto.shoppingCart.ShoppingCartDto;
import ru.yandex.practicum.dto.shoppingCart.ShoppingCartState;
import ru.yandex.practicum.mappers.ShoppingCartMapper;
import ru.yandex.practicum.models.ShoppingCart;
import ru.yandex.practicum.storage.ShoppingCartRepository;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final ShoppingCartRepository repository;


    @Override
    public ShoppingCart getCart(String username) {
        Optional<ShoppingCart> shoppingCartOpt = repository.findByUsername(username);
        ShoppingCart shoppingCart;

        if (shoppingCartOpt.isEmpty()) {
            shoppingCart = new ShoppingCart();
            shoppingCart.setUsername(username);
            shoppingCart.setShoppingCartState(ShoppingCartState.ACTIVE);
            shoppingCart.setProducts(new HashMap<>());
            shoppingCart = repository.save(shoppingCart);
        } else {
            shoppingCart = shoppingCartOpt.get();
        }
        return shoppingCart;
    }

    @Override
    public ShoppingCartDto getShoppingCartByUsername(String username) {
        checkUser(username);
        return ShoppingCartMapper.toDto(getCart(username));
    }

    @Override
    public ShoppingCartDto addProductsToCart(String username, Map<UUID, Long> items) {
        checkUser(username);
        if (items == null || items.isEmpty()) {
            throw new IllegalArgumentException("items пуста");
        }

        ShoppingCart shoppingCart = getCart(username);

        items.forEach((productId, quantity) ->
                shoppingCart.getProducts().merge(productId, quantity, Long::sum));

        ShoppingCart savedCart = repository.save(shoppingCart);

        return ShoppingCartMapper.toDto(savedCart);
    }

    @Override
    public void deactivateShoppingCart(String username) {
        checkUser(username);
        ShoppingCart shoppingCart = getCart(username);
        shoppingCart.setShoppingCartState(ShoppingCartState.DEACTIVATED);
        repository.save(shoppingCart);
    }

    @Override
    public ShoppingCartDto removeProductsFromCart(String username, List<UUID> productIds) {
        checkUser(username);
        if (productIds == null || productIds.isEmpty()) {
            throw new IllegalArgumentException("Список продуктов для удаления пуст");
        }

        ShoppingCart shoppingCart = getCart(username);
        for (UUID id : productIds) {
            shoppingCart.getProducts().remove(id);
        }

        shoppingCart = repository.save(shoppingCart);
        return ShoppingCartMapper.toDto(shoppingCart);
    }

    @Override
    public ShoppingCartDto changeQuantity(String username, ChangeProductQuantityRequest quantityRequest) {
        checkUser(username);
        ShoppingCart shoppingCart = getCart(username);
        shoppingCart.getProducts().put(quantityRequest.getProductId(), quantityRequest.getNewQuantity());

        repository.save(shoppingCart);
        return ShoppingCartMapper.toDto(shoppingCart);
    }

    private void checkUser(String username) {
        if (username == null || username.isEmpty()) {
            throw new NotFoundException("Пользователь не найден");
        }
    }
}
