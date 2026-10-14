package com.codigoaumentado.orders.application.port;

import com.codigoaumentado.orders.domain.Order;

public interface OrderNotifier {

    void orderPlaced(Long orderId, Order order);
}
