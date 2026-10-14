package com.codigoaumentado.orders.application;

import static org.assertj.core.api.Assertions.assertThat;

import com.codigoaumentado.orders.application.port.OrderNotifier;
import com.codigoaumentado.orders.application.port.OrderRepository;
import com.codigoaumentado.orders.domain.Order;
import com.codigoaumentado.orders.domain.OrderLine;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class PlaceOrderUseCaseTest {

    private final List<Order> saved = new ArrayList<>();
    private final List<Long> notified = new ArrayList<>();

    private final OrderRepository repository = order -> {
        saved.add(order);
        return 42L;
    };
    private final OrderNotifier notifier = (orderId, order) -> notified.add(orderId);

    private final PlaceOrderUseCase useCase = new PlaceOrderUseCase(repository, notifier);

    @Test
    void savesOrderAndNotifiesWithGeneratedId() {
        Order order = new Order(1L, List.of(new OrderLine("KB-01", BigDecimal.TEN, 1)));

        Long id = useCase.execute(order);

        assertThat(id).isEqualTo(42L);
        assertThat(saved).containsExactly(order);
        assertThat(notified).containsExactly(42L);
    }
}
