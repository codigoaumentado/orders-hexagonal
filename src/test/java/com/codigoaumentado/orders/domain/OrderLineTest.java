package com.codigoaumentado.orders.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

class OrderLineTest {

    @Test
    void calculatesSubtotal() {
        OrderLine line = new OrderLine("KB-01", new BigDecimal("12.50"), 4);

        assertThat(line.subtotal()).isEqualByComparingTo("50.00");
    }

    @Test
    void rejectsNonPositiveQuantity() {
        assertThatThrownBy(() -> new OrderLine("KB-01", BigDecimal.ONE, 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("quantity must be positive");
    }

    @Test
    void rejectsNegativePrice() {
        assertThatThrownBy(() -> new OrderLine("KB-01", new BigDecimal("-1"), 1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("unitPrice must not be negative");
    }
}
