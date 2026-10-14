package com.codigoaumentado.orders;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

@QuarkusTest
class OrderResourceTest {

    @Test
    void placesOrder() {
        given()
                .contentType(ContentType.JSON)
                .body("""
                        {"customerId": 1, "items": [{"sku": "KB-01", "price": 600.00, "quantity": 2}]}
                        """)
                .when().post("/orders")
                .then()
                .statusCode(200)
                .body(notNullValue());
    }
}
