package ru.yandex.practicum.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.dto.order.OrderDto;
import ru.yandex.practicum.dto.payment.PaymentDto;
import ru.yandex.practicum.service.PaymentService;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/payment")
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping
    public PaymentDto createPaymentOrder(@RequestBody OrderDto orderDto) {
        return paymentService.createPaymentOrder(orderDto);
    }

    @PostMapping("/totalCost")
    public Double getTotalCost(@RequestBody OrderDto orderDto) {
        return getTotalCost(orderDto);
    }

    @PostMapping("/refund")
    public void refundPayment(@RequestBody UUID paymentId) {
        paymentService.refundPayment(paymentId);
    }

    @PostMapping("/productCost")
    public Double getProductCost(@RequestBody OrderDto orderDto) {
        return paymentService.getProductCost(orderDto);
    }

    @PostMapping("/failed")
    public void failedPayment(@RequestBody UUID paymentId) {
        paymentService.failedPayment(paymentId);
    }
}
