package ru.yandex.practicum.models;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import org.springframework.boot.autoconfigure.amqp.RabbitConnectionDetails;
import ru.yandex.practicum.dto.delivery.DeliveryState;

import java.util.UUID;

@Data
@Entity
@Table(name = "deliveries")
@Builder
public class Delivery {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "delivery_id", updatable = false, nullable = false)
    private UUID deliveryId;

    @ManyToOne
    @JoinColumn(name = "from_address_id")
    private Address fromAddress;

    @ManyToOne
    @JoinColumn(name = "to_address_id")
    private Address toAddress;

    @Enumerated(EnumType.STRING)
    @Column(name = "delivery_state", nullable = false)
    private DeliveryState deliveryState;

    @Column(name = "order_id", nullable = false)
    private UUID orderId;
}