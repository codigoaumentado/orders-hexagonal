package com.codigoaumentado.orders;

import java.math.BigDecimal;

public record ItemDto(String sku, BigDecimal price, int quantity) {
}
