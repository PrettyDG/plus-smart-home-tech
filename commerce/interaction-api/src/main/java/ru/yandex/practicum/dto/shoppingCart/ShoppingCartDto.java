package ru.yandex.practicum.dto.shoppingCart;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.Map;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShoppingCartDto {
    @NotBlank
    private UUID cartId;

    @NotEmpty
    private Map<UUID, Long> products;
}