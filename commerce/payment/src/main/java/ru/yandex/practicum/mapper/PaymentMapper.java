package ru.yandex.practicum.mapper;

import lombok.experimental.UtilityClass;
import ru.yandex.practicum.dto.payment.PaymentDto;
import ru.yandex.practicum.model.Payment;

@UtilityClass
public class PaymentMapper {
    public static Payment toEntity(PaymentDto paymentDto) {
        return Payment.builder()
                .paymentId(paymentDto.getPaymentId())
                .orderId(paymentDto.getOrderId())
                .totalPayment(paymentDto.getTotalPayment())
                .deliveryTotal(paymentDto.getDeliveryTotal())
                .feeTotal(paymentDto.getFeeTotal())
                .status(paymentDto.getStatus())
                .build();
    }
    public static PaymentDto toDto(Payment payment) {
        return PaymentDto.builder()
                .paymentId(payment.getPaymentId())
                .orderId(payment.getOrderId())
                .totalPayment(payment.getTotalPayment())
                .deliveryTotal(payment.getDeliveryTotal())
                .feeTotal(payment.getFeeTotal())
                .status(payment.getStatus())
                .build();
    }
}
