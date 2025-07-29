package ru.yandex.practicum.service;

import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.dto.order.OrderDto;
import ru.yandex.practicum.dto.payment.PaymentDto;
import ru.yandex.practicum.dto.payment.PaymentStatus;
import ru.yandex.practicum.dto.shoppingStore.ShoppingStoreClient;
import ru.yandex.practicum.mapper.PaymentMapper;
import ru.yandex.practicum.model.Payment;
import ru.yandex.practicum.repository.PaymentRepository;

import java.util.Map;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository repository;
    private final ShoppingStoreClient shoppingStoreClient;


    @Override
    public Payment getPaymentById(UUID id) {
        Payment payment = repository.getById(id);
        if (payment == null) {
            throw new NotFoundException("Ну существует платежа с id - " + id);
        }
        return payment;
    }

    @Override
    @Transactional
    public PaymentDto createPaymentOrder(OrderDto orderDto) {
        log.info("Запрос на создание платежа для заказа - " + orderDto);
        if (orderDto.getTotalPrice() == null || orderDto.getDeliveryPrice() == null || orderDto.getProductPrice() == null) {
            throw new IllegalArgumentException("Price = null");
        }

        Payment payment = Payment.builder()
                .orderId(orderDto.getOrderId())
                .totalPayment(orderDto.getTotalPrice())
                .deliveryTotal(orderDto.getDeliveryPrice())
                .feeTotal(orderDto.getTotalPrice() * 0.1)
                .status(PaymentStatus.PENDING)
                .build();

        return PaymentMapper.toDto(repository.save(payment));
    }

    @Override
    public Double getTotalCost(OrderDto orderDto) {
        log.info("Запрос на получение полной стоимости платежа для заказа - " + orderDto);
        if (orderDto.getTotalPrice() == null || orderDto.getDeliveryPrice() == null || orderDto.getProductPrice() == null) {
            throw new IllegalArgumentException("Price = null");
        }
        Double totalPrice = orderDto.getProductPrice() + orderDto.getDeliveryPrice();
        totalPrice += (orderDto.getTotalPrice() * 0.1);

        return totalPrice;
    }

    @Override
    @Transactional
    public void refundPayment(UUID paymentId) {
        log.info("Успешный платеж с ID - " + paymentId);
        Payment payment = getPaymentById(paymentId);
        payment.setStatus(PaymentStatus.SUCCESS);

        repository.save(payment);
    }

    @Override
    public Double getProductCost(OrderDto orderDto) {
        log.info("Запрос на получение стоимости продуктов для заказа - " + orderDto);
        Map<UUID, Long> products = orderDto.getProducts();
        if (products == null) {
            throw new NotFoundException("Нету товаров в заказе - " + orderDto);
        }

        return products.entrySet().stream()
                .mapToDouble(entry -> {
                    UUID productId = entry.getKey();
                    Long quantity = entry.getValue();
                    Double price = shoppingStoreClient.getProductInfo(productId).getPrice();
                    return price * quantity;
                })
                .sum();
    }

    @Override
    @Transactional
    public void failedPayment(UUID paymentId) {
        log.info("Платёж FAILED с id - " + paymentId);
        Payment payment = getPaymentById(paymentId);
        payment.setStatus(PaymentStatus.FAILED);

        repository.save(payment);
    }
}
