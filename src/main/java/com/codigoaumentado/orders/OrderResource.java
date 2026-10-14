package com.codigoaumentado.orders;

import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import java.util.List;

@Path("/orders")
public class OrderResource {

    @Inject
    OrderService orderService;

    public record PlaceOrderRequest(Long customerId, List<ItemDto> items) {
    }

    @POST
    public Long place(PlaceOrderRequest request) {
        return orderService.placeOrder(request.customerId(), request.items());
    }
}
