package com.codigoaumentado.orders.application.port;

import com.codigoaumentado.orders.domain.Order;

public interface OrderRepository {

    Long save(Order order);
}
