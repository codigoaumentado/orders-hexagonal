package com.codigoaumentado.orders.infrastructure.rest;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;

import com.codigoaumentado.orders.infrastructure.persistence.OrderEntity;
import io.quarkus.mailer.MockMailbox;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@QuarkusTest
class OrderResourceTest {

    @Inject
    EntityManager em;

    @Inject
    MockMailbox mailbox;

    @BeforeEach
    void clearMailbox() {
        mailbox.clear();
    }

    @Test
    void placesOrderPersistsItAndSendsMail() {
        Long id = Long.valueOf(given()
                .contentType(ContentType.JSON)
                .body("""
                        {"customerId": 1, "items": [{"sku": "KB-01", "price": 600.00, "quantity": 2}]}
                        """)
                .when().post("/orders")
                .then()
                .statusCode(200)
                .extract().asString());

        assertThat(em.find(OrderEntity.class, id).total).isEqualByComparingTo("1080.00");
        assertThat(mailbox.getMailsSentTo("cliente@example.com"))
                .singleElement()
                .satisfies(mail -> assertThat(mail.getText()).isEqualTo("Total: 1080.00"));
    }

    @Test
    void rejectsOrderWithoutItems() {
        given()
                .contentType(ContentType.JSON)
                .body("""
                        {"customerId": 1, "items": []}
                        """)
                .when().post("/orders")
                .then()
                .statusCode(400)
                .body("error", equalTo("order must have at least one line"));
    }
}
