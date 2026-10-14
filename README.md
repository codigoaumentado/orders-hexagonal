# Arquitectura Hexagonal en Java — sin humo

Código del video **"Arquitectura Hexagonal en Java explicada SIN humo (con código real)"** del canal **Código Aumentado**.

## Cómo seguir el video

| Tag | Estado |
|---|---|
| `antes` | `OrderService` acoplado: reglas de negocio, JPA y correo en una sola clase |
| `despues` | Arquitectura hexagonal: dominio en Java puro, puertos y adaptadores |

```bash
git checkout antes     # el problema
git checkout despues   # la solución
git diff antes despues # todo el refactor
```

## Estructura (`despues`)

```
com.codigoaumentado.orders
├── domain                 ← reglas de negocio, solo java.*
│   ├── Order
│   └── OrderLine
├── application            ← casos de uso y puertos, solo java.*
│   ├── PlaceOrderUseCase
│   └── port
│       ├── OrderRepository
│       └── OrderNotifier
└── infrastructure         ← aquí vive Quarkus
    ├── config/UseCaseConfig
    ├── notification/MailOrderNotifier
    ├── persistence/JpaOrderRepository, OrderEntity
    └── rest/OrderResource, DomainValidationExceptionMapper
```

**Regla de oro:** las dependencias apuntan hacia adentro. `domain` y `application` no importan ningún framework.

## Requisitos

- Java 21
- Maven 3.9+

Sin Docker: usa H2 en memoria y un mailer simulado.

## Ejecutar

```bash
mvn verify          # tests + cobertura (mínimo 90 %)
mvn quarkus:dev     # http://localhost:8080
```

```bash
curl -X POST localhost:8080/orders \
  -H 'Content-Type: application/json' \
  -d '{"customerId": 1, "items": [{"sku": "KB-01", "price": 600.00, "quantity": 2}]}'
```

## Stack

Quarkus 3.33 LTS · Hibernate ORM · H2 · Quarkus Mailer · JUnit 5 · AssertJ · JaCoCo
