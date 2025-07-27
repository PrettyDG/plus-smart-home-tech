package ru.yandex.practicum.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.models.WarehouseProduct;

import java.util.UUID;

@Repository
public interface WarehouseRepository extends JpaRepository<WarehouseProduct, UUID> {

}
