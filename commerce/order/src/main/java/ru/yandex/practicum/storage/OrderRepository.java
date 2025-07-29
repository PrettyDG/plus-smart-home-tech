package ru.yandex.practicum.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yandex.practicum.models.Order;

import java.util.List;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {
    List<Order> findAllByUsername(String username);
}
