package com.codigoaumentado.orders;

import static org.assertj.core.api.Assertions.assertThat;

import io.quarkus.mailer.MockMailbox;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * ANTES: para probar una multiplicación necesitamos Quarkus, H2 y un mailer simulado.
 */
@QuarkusTest
class OrderServiceTest {

    @Inject
    OrderService orderService;

    @Inject
    EntityManager em;

    @Inject
    MockMailbox mailbox;

    @BeforeEach
    void clearMailbox() {
        mailbox.clear();
    }

    @Test
    void appliesTenPercentDiscountAboveThreshold() {
        Long id = orderService.placeOrder(1L, List.of(new ItemDto("KB-01", new BigDecimal("600.00"), 2)));

        assertThat(em.find(OrderEntity.class, id).total).isEqualByComparingTo("1080.00");
        assertThat(mailbox.getMailsSentTo("cliente@example.com")).hasSize(1);
    }

    @Test
    void doesNotApplyDiscountAtThreshold() {
        Long id = orderService.placeOrder(1L, List.of(new ItemDto("KB-01", new BigDecimal("500.00"), 2)));

        assertThat(em.find(OrderEntity.class, id).total).isEqualByComparingTo("1000.00");
    }
}
