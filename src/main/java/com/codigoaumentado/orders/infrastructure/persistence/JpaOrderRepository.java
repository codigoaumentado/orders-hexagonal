package com.codigoaumentado.orders.infrastructure.persistence;

import com.codigoaumentado.orders.application.port.OrderRepository;
import com.codigoaumentado.orders.domain.Order;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class JpaOrderRepository implements OrderRepository {

    private final EntityManager em;

    public JpaOrderRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    @Transactional
    public Long save(Order order) {
        OrderEntity entity = new OrderEntity(order.customerId(), order.total());
        em.persist(entity);
        return entity.id;
    }
}
