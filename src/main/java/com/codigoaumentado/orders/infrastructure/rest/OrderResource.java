package com.codigoaumentado.orders.infrastructure.rest;

import com.codigoaumentado.orders.application.PlaceOrderUseCase;
import com.codigoaumentado.orders.domain.Order;
import com.codigoaumentado.orders.domain.OrderLine;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import java.math.BigDecimal;
import java.util.List;

@Path("/orders")
public class OrderResource {

    private final PlaceOrderUseCase placeOrder;

    public OrderResource(PlaceOrderUseCase placeOrder) {
        this.placeOrder = placeOrder;
    }

    public record ItemRequest(String sku, BigDecimal price, int quantity) {
    }

    public record PlaceOrderRequest(Long customerId, List<ItemRequest> items) {
    }

    @POST
    public Long place(PlaceOrderRequest request) {
        List<OrderLine> lines = request.items().stream()
                .map(item -> new OrderLine(item.sku(), item.price(), item.quantity()))
                .toList();
        return placeOrder.execute(new Order(request.customerId(), lines));
    }
}
