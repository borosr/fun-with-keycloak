package hu.borosr.fun.controller;

import com.github.javafaker.Faker;
import hu.borosr.fun.dto.ProductDTO;
import hu.borosr.fun.persistence.Role;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;

import static hu.borosr.fun.common.UserHelper.getAuthorization;
import static hu.borosr.fun.common.UserHelper.login;
import static hu.borosr.fun.loader.DataLoader.USER;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
class ProductControllerIntTest {

    @LocalServerPort
    private int port;
    private static final Faker faker = new Faker();

    @BeforeEach
    private void setUp() {
        RestAssured.port = port;
    }

    @Test
    void findAll() {
        given().header(getAuthorization(login(Role.USER))).log().all()
                .when().get("/api/products")
                .then().contentType(ContentType.JSON)
                .statusCode(200);
    }

    @Test
    void findById() {
        ProductDTO product = createProduct();
        given().header(getAuthorization(login(Role.USER))).log().all()
                .when().get("/api/products/"+product.getId())
                .then().log().all()
                .contentType(ContentType.JSON)
                .statusCode(200)
                .body("$", notNullValue())
                .body("name", equalTo(product.getName()))
                .body("price", equalTo(product.getPrice().toString()))
                .body("createdBy", equalTo(USER))
                .body("createdAt", notNullValue());
    }

    @Test
    void deleteById() {
        ProductDTO product = createProduct();
        given().header(getAuthorization(login(Role.USER))).log().all()
                .when().delete("/api/products/"+product.getId())
                .then().log().all()
                .statusCode(202);
    }

    @Test
    void create() {
        createProduct();
    }

    @Test
    void update() {
        ProductDTO product = createProduct();
        String name = faker.funnyName().name();
        BigDecimal price = BigDecimal.valueOf(faker.number().numberBetween(100, 10000));
        given().header(getAuthorization(login(Role.USER))).log().all()
                .body(ProductDTO.builder()
                        .name(name)
                        .price(price)
                        .build())
                .contentType(ContentType.JSON)
                .when().put("/api/products/"+product.getId())
                .then().log().all()
                .contentType(ContentType.JSON)
                .statusCode(200)
                .body("$", notNullValue())
                .body("name", equalTo(name))
                .body("price", equalTo(price.intValue()))
                .body("createdBy", equalTo(USER))
                .body("createdAt", notNullValue());
    }

    private static ProductDTO createProduct() {
        String name = faker.funnyName().name();
        BigDecimal price = BigDecimal.valueOf(faker.number().numberBetween(100, 10000));
        return given().header(getAuthorization(login(Role.USER))).log().all()
                .body(ProductDTO.builder()
                        .name(name)
                        .price(price)
                        .build())
                .contentType(ContentType.JSON)
                .when().post("/api/products")
                .then().log().all()
                .contentType(ContentType.JSON)
                .statusCode(201)
                .body("$", notNullValue())
                .body("name", equalTo(name))
                .body("price", equalTo(price.intValue()))
                .body("createdBy", equalTo(USER))
                .body("createdAt", notNullValue())
                .extract().body().as(ProductDTO.class);
    }
}