package com.codigoaumentado.orders.application;

import com.codigoaumentado.orders.application.port.OrderNotifier;
import com.codigoaumentado.orders.application.port.OrderRepository;
import com.codigoaumentado.orders.domain.Order;

public class PlaceOrderUseCase {

    private final OrderRepository repository;
    private final OrderNotifier notifier;

    public PlaceOrderUseCase(OrderRepository repository, OrderNotifier notifier) {
        this.repository = repository;
        this.notifier = notifier;
    }

    public Long execute(Order order) {
        Long id = repository.save(order);
        notifier.orderPlaced(id, order);
        return id;
    }
}
