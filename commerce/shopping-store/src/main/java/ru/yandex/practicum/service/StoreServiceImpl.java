package ru.yandex.practicum.service;

import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.dto.shoppingStore.ProductCategory;
import ru.yandex.practicum.dto.shoppingStore.ProductDto;
import ru.yandex.practicum.dto.shoppingStore.ProductState;
import ru.yandex.practicum.dto.shoppingStore.QuantityState;
import ru.yandex.practicum.mappers.ProductListMapper;
import ru.yandex.practicum.mappers.ProductMapper;
import ru.yandex.practicum.models.Product;
import ru.yandex.practicum.storage.ShoppingStoreRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {
    private final ShoppingStoreRepository repository;

    @Override
    public List<ProductDto> getProductsByCategoryName(ProductCategory productCategory, Pageable pageable) {
        List<Product> products = repository.findAllByProductCategory(productCategory, pageable);
        return ProductListMapper.toProductDtoList(products);
    }

    @Override
    public ProductDto createProduct(ProductDto productDto) {
        Product product = ProductMapper.toEntity(productDto);

        if (product.getProductId() == null) {
            product.setProductId(UUID.randomUUID());
        }

        if (repository.existsById(product.getProductId())) {
            throw new IllegalArgumentException("Уже существует товар с ID - " + product.getProductId());
        }

        return ProductMapper.toDto(repository.save(product));
    }

    @Override
    public ProductDto updateProduct(ProductDto productDto) {
        if (productDto.getProductId() == null || !repository.existsById(productDto.getProductId())) {
            throw new IllegalArgumentException("Такого товара не существует");
        }

        repository.save(ProductMapper.toEntity(productDto));

        return productDto;
    }

    @Override
    public boolean removeProduct(UUID productId) {
        if (repository.existsById(productId)) {
            Product product = repository.getReferenceById(productId);
            product.setProductState(ProductState.DEACTIVATE);
            repository.save(product);

            return true;
        }
        return false;
    }

    @Override
    public boolean changeQuantityState(UUID productID, QuantityState quantityState) {
        if (repository.existsById(productID)) {
            Product product = ProductMapper.toEntity(getProductById(productID));
            product.setQuantityState(quantityState);
            repository.save(product);

            return true;
        }
        return false;
    }

    @Override
    public ProductDto getProductById(UUID productId) {
        Optional<Product> product = repository.findById(productId);
        if (product.isEmpty()) {
            throw new NotFoundException("Не найден продукт с id - " + productId);
        }

        return ProductMapper.toDto(product.get());
    }
}
