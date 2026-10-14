package com.codigoaumentado.orders;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "orders")
public class OrderEntity {

    @Id
    @GeneratedValue
    public Long id;

    @Column(name = "customer_id", nullable = false)
    public Long customerId;

    @Column(nullable = false, precision = 12, scale = 2)
    public BigDecimal total;

    protected OrderEntity() {
    }

    public OrderEntity(Long customerId, BigDecimal total) {
        this.customerId = customerId;
        this.total = total;
    }
}
