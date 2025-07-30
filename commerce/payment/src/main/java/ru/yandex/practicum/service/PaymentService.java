package ru.yandex.practicum.service;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.dto.order.OrderDto;
import ru.yandex.practicum.dto.payment.PaymentDto;
import ru.yandex.practicum.model.Payment;

import java.util.UUID;

public interface PaymentService {

    Payment getPaymentById (UUID id);

    PaymentDto createPaymentOrder(OrderDto orderDto);

    Double getTotalCost(OrderDto orderDto);

    void refundPayment(UUID paymentId);

    Double getProductCost(OrderDto orderDto);

    void failedPayment(UUID paymentId);
}
