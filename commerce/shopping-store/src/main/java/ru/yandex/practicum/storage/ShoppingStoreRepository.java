package ru.yandex.practicum.storage;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.yandex.practicum.dto.shoppingStore.ProductCategory;
import ru.yandex.practicum.models.Product;

import java.util.List;
import java.util.UUID;

public interface ShoppingStoreRepository extends JpaRepository<Product, UUID> {
    List<Product> findAllByProductCategory(ProductCategory productCategory, Pageable pageable);
}
