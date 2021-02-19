package hu.borosr.fun.common;

import hu.borosr.fun.dto.UserDTO;
import hu.borosr.fun.entity.Role;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import org.keycloak.representations.AccessTokenResponse;

import static hu.borosr.fun.loader.DataLoader.ADMIN;
import static hu.borosr.fun.loader.DataLoader.USER;
import static io.restassured.RestAssured.given;

public class UserHelper {

    public static Header getAuthorization(String token) {
        return new Header("Authorization", "Bearer " + token);
    }

    public static String login(Role role) {
        var resp = given().log().all()
                .body(getUser(role))
                .contentType(ContentType.JSON)
                .when().post("/api/login")
                .then()
                .assertThat()
                .contentType(ContentType.JSON)
                .statusCode(200)
                .extract().body().as(AccessTokenResponse.class);
        return resp.getToken();
    }

    private static UserDTO getUser(Role role) {
        if (role == Role.ADMIN) {
            return UserDTO.builder().username(ADMIN).password(ADMIN).build();
        }
        return UserDTO.builder().username(USER).password(USER).build();
    }
}
