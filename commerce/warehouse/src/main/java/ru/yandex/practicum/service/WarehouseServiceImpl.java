package ru.yandex.practicum.service;

import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.dto.shoppingCart.ShoppingCartDto;
import ru.yandex.practicum.dto.warehouse.AddProductToWarehouseRequest;
import ru.yandex.practicum.dto.warehouse.AddressDto;
import ru.yandex.practicum.dto.warehouse.BookedProductsDto;
import ru.yandex.practicum.dto.warehouse.NewWarehouseProduct;
import ru.yandex.practicum.models.WarehouseProduct;
import ru.yandex.practicum.storage.WarehouseRepository;

import java.security.SecureRandom;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WarehouseServiceImpl implements WarehouseService {
    private final WarehouseRepository repository;
    private AddressDto address = makeAdress();

    @Transactional
    @Override
    public void addNewProductInWarehouse(NewWarehouseProduct newWarehouseProduct) {
        if (repository.existsById(newWarehouseProduct.getProductId())) {
            throw new IllegalArgumentException("Уже существует товар с ID - " + newWarehouseProduct.getProductId());
        }

        WarehouseProduct product = WarehouseProduct.builder()
                .productId(newWarehouseProduct.getProductId())
                .fragile(newWarehouseProduct.isFragile())
                .width(newWarehouseProduct.getDimension().getWidth())
                .height(newWarehouseProduct.getDimension().getHeight())
                .depth(newWarehouseProduct.getDimension().getDepth())
                .weight(newWarehouseProduct.getWeight())
                .quantity(0)
                .build();

        repository.save(product);
    }

    @Override
    public BookedProductsDto checkAlreadyBookedProductsInWarehouse(ShoppingCartDto shoppingCartDto) {
        Map<UUID, Long> productsMap = shoppingCartDto.getProducts();
        List<WarehouseProduct> warehouseProductList = repository.findAllById(productsMap.keySet());

        Map<UUID, WarehouseProduct> warehouseProductMap = warehouseProductList.stream()
                .collect(Collectors.toMap(WarehouseProduct::getProductId, Function.identity()));

        checkAvailabilityProductsInWarehouse(productsMap.keySet(), warehouseProductMap.keySet());

        checkQuantity(productsMap, warehouseProductMap);

        return bookingProducts(warehouseProductList);
    }

    @Transactional
    @Override
    public void addProductToWarehouse(AddProductToWarehouseRequest request) {
        Optional<WarehouseProduct> productOpt = repository.findById(request.getProductId());
        if (productOpt.isEmpty()) {
            throw new NotFoundException("Отсутствует товар - " + request);
        }
        WarehouseProduct product = productOpt.get();
        product.setQuantity(product.getQuantity() + request.getQuantity());
        repository.save(product);
    }

    @Override
    public AddressDto getAdressWarehouse() {
        return address;
    }

    private AddressDto makeAdress() {
        String[] addresses = new String[]{"ADDRESS_1", "ADDRESS_2"};
        String currentAddresses = addresses[Random.from(new SecureRandom()).nextInt(0, 1)];

        AddressDto addressDto = new AddressDto();
        addressDto.setCountry(currentAddresses);
        addressDto.setCity(currentAddresses);
        addressDto.setStreet(currentAddresses);
        addressDto.setHouse(currentAddresses);
        addressDto.setFlat(currentAddresses);

        return addressDto;
    }

    private void checkAvailabilityProductsInWarehouse(Set<UUID> productsInCart, Set<UUID> productsInWarehouse) {
        productsInCart.removeAll(productsInWarehouse);

        if (!productsInCart.isEmpty()) {
            throw new NotFoundException("На складе нет товаров: " + productsInCart);
        }
    }

    private void checkQuantity(Map<UUID, Long> productsInCart, Map<UUID, WarehouseProduct> warehouseProductsMap) {
        List<UUID> notAvailabilityProducts = new ArrayList<>();
        for (UUID id : productsInCart.keySet()) {
            if (productsInCart.get(id) > warehouseProductsMap.get(id).getQuantity()) {
                notAvailabilityProducts.add(id);
            }
        }

        if (!notAvailabilityProducts.isEmpty()) {
            throw new IllegalArgumentException("На складе не хватает товаров: "
                    + notAvailabilityProducts);
        }
    }

    private BookedProductsDto bookingProducts(List<WarehouseProduct> products) {
        BookedProductsDto result = new BookedProductsDto(0.0, 0.0, false);
        for (WarehouseProduct product : products) {
            result.setDeliveryVolume(result.getDeliveryVolume() + product.getWeight());
            result.setDeliveryVolume(result.getDeliveryVolume()
                    + product.getWidth() * product.getHeight() * product.getDepth());
            if (product.isFragile()) {
                result.setFragile(true);
            }
        }

        return result;
    }

}
