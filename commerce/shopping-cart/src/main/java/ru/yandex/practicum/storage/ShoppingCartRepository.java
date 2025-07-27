package ru.yandex.practicum.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yandex.practicum.models.ShoppingCart;

import java.util.Optional;
import java.util.UUID;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, UUID> {
    Optional<ShoppingCart> findByUsername(String username);
}
