package com.montanha.cenario.auth;

import com.montanha.factory.UserDataFactory;
import com.montanha.model.Usuario;
import static io.restassured.RestAssured.*;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AuthTest {
    private UserDataFactory userDataFactory;

    @BeforeEach
    public void setUp() {
        baseURI = "http://localhost";
        port = 8089;
        basePath = "/api";

        userDataFactory = new UserDataFactory();
    }

    @Test
    public void testAdminComSenhaValidaRetornaSucesso() {
        Usuario aValidAdmin = userDataFactory.aValidAdmin();

        given()
            .contentType(ContentType.JSON)
            .body(aValidAdmin)
        .when()
            .post("/v1/auth")
        .then()
            .assertThat()
                .statusCode(HttpStatus.SC_OK);
    }
}