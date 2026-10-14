package com.codigoaumentado.orders;

import io.quarkus.mailer.Mail;
import io.quarkus.mailer.Mailer;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 * ANTES: reglas de negocio, persistencia y notificaciones mezcladas en una sola clase.
 * Para probar el descuento hay que levantar Quarkus, la base de datos y el mailer.
 */
@ApplicationScoped
public class OrderService {

    @Inject
    EntityManager em;

    @Inject
    Mailer mailer;

    @Transactional
    public Long placeOrder(Long customerId, List<ItemDto> items) {
        BigDecimal total = BigDecimal.ZERO;
        for (ItemDto item : items) {
            total = total.add(item.price().multiply(BigDecimal.valueOf(item.quantity())));
        }
        if (total.compareTo(new BigDecimal("1000")) > 0) {
            total = total.multiply(new BigDecimal("0.90"));
        }
        OrderEntity entity = new OrderEntity(customerId, total.setScale(2, RoundingMode.HALF_UP));
        em.persist(entity);
        mailer.send(Mail.withText("cliente@example.com", "Pedido creado", "Total: " + entity.total));
        return entity.id;
    }
}
