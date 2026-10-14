package com.codigoaumentado.orders.infrastructure.config;

import com.codigoaumentado.orders.application.PlaceOrderUseCase;
import com.codigoaumentado.orders.application.port.OrderNotifier;
import com.codigoaumentado.orders.application.port.OrderRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;

public class UseCaseConfig {

    @Produces
    @ApplicationScoped
    PlaceOrderUseCase placeOrderUseCase(OrderRepository repository, OrderNotifier notifier) {
        return new PlaceOrderUseCase(repository, notifier);
    }
}
