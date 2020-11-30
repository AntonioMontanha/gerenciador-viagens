package com.montanha.gerenciador.controller;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import com.montanha.gerenciador.pojo.UsuarioPOJO;
import io.restassured.http.ContentType;
import org.junit.Before;
import org.junit.Test;

public class ViagemControllerAcceptanceTest {

    private String token;

    @Before
    public void setUp() {
        baseURI = "http://localhost";
        port = 8089;
        basePath = "/api";

        UsuarioPOJO usuario = new UsuarioPOJO("usuario@email.com", "123456");

        this.token = given()
                .contentType(ContentType.JSON)
                .body(usuario)
            .when()
                .post("/v1/auth")
            .then()
                .extract()
                    .path("data.token");
    }

    @Test
    public void testViajanteVeATemperaturaDeUmDestino() {
        given()
            .header("Authorization", token)
        .when()
            .get("/v1/viagens/1")
        .then()
            .assertThat()
                .statusCode(200)
                .body("data.temperatura", equalTo(30.00f));
    }

    @Test
    public void testViajanteEAlertadoSobreAIndisponibilidadeDaTemperatura() {
        given()
            .header("Authorization", token)
        .when()
            .get("/v1/viagens/2")
        .then()
            .assertThat()
                .statusCode(502)
                .body("errors", containsInAnyOrder("502 Não conseguimos identificar a temperatura"));
    }
}
