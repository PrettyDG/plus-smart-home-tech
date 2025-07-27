package ru.yandex.practicum.models;

import jakarta.persistence.*;
import lombok.*;
import ru.yandex.practicum.dto.shoppingCart.ShoppingCartState;

import java.util.Map;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "shopping_carts")
public class ShoppingCart {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "cart_id", updatable = false, nullable = false)
    private UUID cartId;

    @Column(name = "username")
    private String username;

    @Enumerated(EnumType.STRING)
    @Column(name = "state")
    private ShoppingCartState shoppingCartState;

    @ElementCollection
    @CollectionTable(name = "products_in_carts", joinColumns = @JoinColumn(name = "cart_id"))
    @MapKeyColumn(name = "product_id")
    @Column(name = "quantity")
    private Map<UUID, Long> products;
}
