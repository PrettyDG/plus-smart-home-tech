package ru.yandex.practicum.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "warehouse_products")
public class WarehouseProduct {

    @Id
    @Column(name = "product_id")
    private UUID productId;

    @Column(name = "fragile")
    private boolean fragile;

    @Column(name = "width")
    private double width;

    @Column(name = "height")
    private double height;

    @Column(name = "depth")
    private double depth;

    @Column(name = "weight")
    private double weight;

    @Column(name = "quantity")
    private Integer quantity;
}