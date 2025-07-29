package ru.yandex.practicum.dto.payment;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.dto.order.OrderDto;

@FeignClient(name = "payment", path = "/api/v1/payment")
public interface PaymentClient {
    @PostMapping
    PaymentDto addPayment(@RequestBody OrderDto orderDto);

    @PostMapping("/totalCost")
    double getTotalCost(@RequestBody OrderDto orderDto);

    @PostMapping("/productCost")
    double getProductsCost(@RequestBody OrderDto orderDto);
}