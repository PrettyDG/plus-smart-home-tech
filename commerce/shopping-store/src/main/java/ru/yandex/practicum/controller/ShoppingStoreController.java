package ru.yandex.practicum.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.dto.shoppingStore.ProductCategory;
import ru.yandex.practicum.dto.shoppingStore.ProductDto;
import ru.yandex.practicum.dto.shoppingStore.QuantityState;
import ru.yandex.practicum.service.StoreService;

import java.util.List;
import java.util.UUID;

@RestController
@Validated
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/shopping-store")
public class ShoppingStoreController {
    private final StoreService storeService;

    @GetMapping
    public Page<ProductDto> getProductsByCategoryName(@RequestParam(name = "category") ProductCategory productCategory, Pageable pageable) {
        log.info("Получен запрос на получения списка товаров категории - {}, и страницам - {}", productCategory, pageable);
        List<ProductDto> productDtos = storeService.getProductsByCategoryName(productCategory, pageable);

        return new PageImpl<>(productDtos, pageable, productDtos.size());
    }

    @PutMapping
    public ProductDto createProduct(@RequestBody ProductDto productDto) {
        log.info("Получен запрос на создание продукта - {}", productDto);
        return storeService.createProduct(productDto);
    }

    @PostMapping
    public ProductDto updateProduct(@RequestBody ProductDto productDto) {
        log.info("Запрос на обновление продукта - {}", productDto);
        return storeService.updateProduct(productDto);
    }

    @PostMapping("/removeProductFromStore")
    public boolean removeProduct(@RequestBody UUID productId) {
        log.info("Получен запрос на удаление продукта - {}", productId);
        return storeService.removeProduct(productId);
    }

    @PostMapping("/quantityState")
    public boolean changeQuantityState(@RequestParam UUID productId, @RequestParam QuantityState quantityState) {
        log.info("Получен запрос на изменение статуа по товару с id - {}, новый статус - {}", productId, quantityState);
        return storeService.changeQuantityState(productId, quantityState);
    }

    @GetMapping("/{productId}")
    public ProductDto getProductById(@PathVariable("productId") UUID productId) {
        log.info("Получен запрос на получение продукта по id - ", productId);
        return storeService.getProductById(productId);
    }
}