package com.codigoaumentado.orders.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Objects;

public record Order(Long customerId, List<OrderLine> lines) {

    private static final BigDecimal DISCOUNT_THRESHOLD = new BigDecimal("1000");
    private static final BigDecimal DISCOUNT_FACTOR = new BigDecimal("0.90");

    public Order {
        Objects.requireNonNull(customerId, "customerId is required");
        lines = List.copyOf(Objects.requireNonNull(lines, "lines are required"));
        if (lines.isEmpty()) {
            throw new IllegalArgumentException("order must have at least one line");
        }
    }

    public BigDecimal total() {
        BigDecimal gross = lines.stream()
                .map(OrderLine::subtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal net = gross.compareTo(DISCOUNT_THRESHOLD) > 0
                ? gross.multiply(DISCOUNT_FACTOR)
                : gross;
        return net.setScale(2, RoundingMode.HALF_UP);
    }
}
