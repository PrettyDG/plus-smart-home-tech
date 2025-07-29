package ru.yandex.practicum.model;

import jakarta.persistence.*;
import lombok.*;
import ru.yandex.practicum.dto.payment.PaymentStatus;

import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "payments")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "payment_id", nullable = false)
    private UUID paymentId;

    @Column(name = "order_id")
    private UUID orderId;

    @Column(name = "total_payment")
    private double totalPayment;

    @Column(name = "delivery_total")
    private double deliveryTotal;

    @Column(name = "fee_total")
    private double feeTotal;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;
}