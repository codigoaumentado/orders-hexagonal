package com.codigoaumentado.orders.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class OrderTest {

    @Test
    void appliesTenPercentDiscountAboveThreshold() {
        Order order = new Order(1L, List.of(new OrderLine("KB-01", new BigDecimal("600.00"), 2)));

        assertThat(order.total()).isEqualByComparingTo("1080.00");
    }

    @Test
    void doesNotApplyDiscountAtThreshold() {
        Order order = new Order(1L, List.of(new OrderLine("KB-01", new BigDecimal("500.00"), 2)));

        assertThat(order.total()).isEqualByComparingTo("1000.00");
    }

    @Test
    void sumsAllLinesAndRoundsToTwoDecimals() {
        Order order = new Order(1L, List.of(
                new OrderLine("MS-01", new BigDecimal("19.995"), 1),
                new OrderLine("PAD-01", new BigDecimal("5.00"), 3)));

        assertThat(order.total()).isEqualTo(new BigDecimal("35.00"));
    }

    @Test
    void rejectsEmptyOrder() {
        assertThatThrownBy(() -> new Order(1L, List.of()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("order must have at least one line");
    }

    @Test
    void rejectsMissingCustomer() {
        List<OrderLine> lines = List.of(new OrderLine("KB-01", BigDecimal.ONE, 1));

        assertThatThrownBy(() -> new Order(null, lines))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    void isNotAffectedByLaterChangesToTheOriginalList() {
        List<OrderLine> lines = new ArrayList<>(List.of(new OrderLine("KB-01", BigDecimal.TEN, 1)));
        Order order = new Order(1L, lines);

        lines.add(new OrderLine("KB-02", BigDecimal.TEN, 1));

        assertThat(order.lines()).hasSize(1);
    }
}
