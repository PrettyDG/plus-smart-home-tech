package ru.yandex.practicum.service;

import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.ast.Or;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.dto.delivery.DeliveryClient;
import ru.yandex.practicum.dto.order.CreateNewOrderRequest;
import ru.yandex.practicum.dto.order.OrderDto;
import ru.yandex.practicum.dto.order.OrderState;
import ru.yandex.practicum.dto.order.ProductReturnRequest;
import ru.yandex.practicum.dto.payment.PaymentClient;
import ru.yandex.practicum.mappers.OrderMapper;
import ru.yandex.practicum.models.Order;
import ru.yandex.practicum.storage.OrderRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class OrderServiceImpl implements OrderService {
    private OrderRepository repository;
    private PaymentClient paymentClient;
    private DeliveryClient deliveryClient;

    @Override
    @Transactional(readOnly = true)
    public Order getOrderById(UUID id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderDto> getOrdersByUser(String username) {
        log.info("Запрос на получение всех заказов пользователя - " + username);
        if (username == null || username.isBlank()) {
            throw new NotFoundException("Username пуст");
        }

        List<Order> orders = repository.findAllByUsername(username);
        if (orders.isEmpty()) {
            throw new NotFoundException("Нет заказов у пользователя - " + username);
        }

        return OrderMapper.toDtoList(orders);
    }

    @Override
    public OrderDto createNewOrder(CreateNewOrderRequest orderRequest) {
        log.info("Запрос на создание заказа - " + orderRequest);
        Order order = Order.builder()
                .orderId(UUID.randomUUID())
                .shoppingCartId(orderRequest.getShoppingCart().getCartId())
                .products(orderRequest.getShoppingCart().getProducts())
                .state(OrderState.NEW)
                .build();

        order = repository.save(order);

        return OrderMapper.toDto(order);
    }

    @Override
    public OrderDto returnOrder(ProductReturnRequest returnRequest) {
        log.info("Запрос на возврат заказа - " + returnRequest);
        Order order = getOrderById(returnRequest.getOrderId());
        changeOrderState(order.getOrderId(), OrderState.PRODUCT_RETURNED);

        return OrderMapper.toDto(order);
    }

    @Override
    public OrderDto paymentOrder(UUID orderId) {
        log.info("Запрос на оплату заказа с id - " + orderId);
        Order order = getOrderById(orderId);
        changeOrderState(orderId, OrderState.PAID);

        return OrderMapper.toDto(order);
    }

    @Override
    public OrderDto paymentFailedOrder(UUID orderId) {
        log.info("Оплата заказа FAILED - " + orderId);
        Order order = getOrderById(orderId);
        changeOrderState(orderId, OrderState.PAYMENT_FAILED);

        return OrderMapper.toDto(order);
    }

    @Override
    public OrderDto deliveryOrder(UUID orderId) {
        log.info("Запрос на доставку заказа - " + orderId);
        Order order = getOrderById(orderId);
        changeOrderState(orderId, OrderState.ON_DELIVERY);

        return OrderMapper.toDto(order);
    }

    @Override
    public OrderDto deliveryFailedOrder(UUID orderId) {
        log.info("Доставка FAILED у заказа - " + orderId);
        Order order = getOrderById(orderId);
        changeOrderState(orderId, OrderState.DELIVERY_FAILED);

        return OrderMapper.toDto(order);
    }

    @Override
    public OrderDto completedOrder(UUID orderId) {
        log.info("Заказа завершён - " + orderId);
        Order order = getOrderById(orderId);
        changeOrderState(orderId, OrderState.COMPLETED);

        return OrderMapper.toDto(order);
    }

    @Override
    public OrderDto calculateTotalOrder(UUID orderId) {
        log.info("Запрос на подсчёт полной стоимости заказа " + orderId);
        Order order = getOrderById(orderId);
        order.setTotalPrice(paymentClient.getTotalCost(OrderMapper.toDto(order)));
        return OrderMapper.toDto(repository.save(order));
    }

    @Override
    public OrderDto calculateDeliveryOrder(UUID orderId) {
        log.info("Запрос на подсчёт стоимости доставки заказа - " + orderId);
        Order order = getOrderById(orderId);
        order.setDeliveryPrice(deliveryClient.getCostDelivery(OrderMapper.toDto(order)));
        return OrderMapper.toDto(repository.save(order));
    }

    @Override
    public OrderDto assemblyOrder(UUID orderId) {
        Order order = getOrderById(orderId);
        order.setState(OrderState.ASSEMBLED);
        return OrderMapper.toDto(repository.save(order));
    }

    @Override
    public OrderDto assemblyFailedOrder(UUID orderId) {
        Order order = getOrderById(orderId);
        order.setState(OrderState.ASSEMBLY_FAILED);
        return OrderMapper.toDto(repository.save(order));
    }

    private Order changeOrderState (UUID orderId, OrderState orderState) {
        Order order = getOrderById(orderId);
        order.setState(orderState);
        return repository.save(order);
    }
}
