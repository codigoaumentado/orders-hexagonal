package com.codigoaumentado.orders.domain;

import java.math.BigDecimal;
import java.util.Objects;

public record OrderLine(String sku, BigDecimal unitPrice, int quantity) {

    public OrderLine {
        Objects.requireNonNull(sku, "sku is required");
        Objects.requireNonNull(unitPrice, "unitPrice is required");
        if (unitPrice.signum() < 0) {
            throw new IllegalArgumentException("unitPrice must not be negative");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("quantity must be positive");
        }
    }

    public BigDecimal subtotal() {
        return unitPrice.multiply(BigDecimal.valueOf(quantity));
    }
}
