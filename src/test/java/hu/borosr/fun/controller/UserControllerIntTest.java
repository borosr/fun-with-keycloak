package hu.borosr.fun.controller;

import com.github.javafaker.Faker;
import hu.borosr.fun.dto.UserDTO;
import hu.borosr.fun.entity.Role;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static hu.borosr.fun.common.UserHelper.getAuthorization;
import static hu.borosr.fun.common.UserHelper.login;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;

@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
class UserControllerIntTest {

    @LocalServerPort
    private int port;
    private static final Faker faker = new Faker();


    @BeforeEach
    private void setUp() {
        RestAssured.port = port;
    }

    @Test
    void testSignUp() {
        String username = faker.name().username();
        given().log().all()
                .body(UserDTO.builder().username(username).password(username).fullName(faker.name().fullName()).build())
                .contentType(ContentType.JSON)
                .when().post("/api/users")
                .then()
                .assertThat()
                .statusCode(201);
    }

    @Test
    void testLoginWithUser() {
        login(Role.USER);
    }

    @Test
    void testLoginWithAdmin() {
        login(Role.ADMIN);
    }

    @Test
    void testUsers() {
        var token = login(Role.ADMIN);
        given().log().all()
                .header(getAuthorization(token))
                .contentType(ContentType.JSON)
                .when().get("/api/users")
                .then()
                .assertThat()
                .contentType(ContentType.JSON)
                .statusCode(200)
        .body("$", notNullValue());
    }
}